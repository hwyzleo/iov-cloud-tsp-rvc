package net.hwyz.iov.cloud.tsp.rvc.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.ClientAccount;
import net.hwyz.iov.cloud.tsp.framework.commons.enums.AccountType;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.request.ControlRequest;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service.ExTboxService;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.factory.RvcFactory;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository.VehicleRvcRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.service.RvcService;
import net.hwyz.iov.cloud.tsp.rvc.service.facade.assembler.ControlResponseAssembler;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.request.RemoteControlRequest;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.response.TboxCmdResponse;
import org.springframework.stereotype.Service;

/**
 * 远程车辆控制相关应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RvcAppService {

    private final RvcService rvcService;
    private final RvcFactory rvcFactory;
    private final ExTboxService exTboxService;
    private final VehicleRvcRepository vehicleRvcRepository;

    /**
     * 寻车
     *
     * @param controlRequest 车控请求
     * @param clientAccount  客户端账号
     * @return 车控返回
     */
    public ControlResponse findVehicle(ControlRequest controlRequest, ClientAccount clientAccount) {
        String vin = controlRequest.getVin();
        logger.info("用户[{}]执行远控：寻车[{}]", clientAccount.getUid(), vin);
        VehicleRvcDo vehicleRvcDo = rvcService.getOrCreate(vin);
        TboxCmdResponse tboxCmd = exTboxService.remoteControl(RemoteControlRequest.builder()
                .vin(vin)
                .type(RemoteControlType.FIND_VEHICLE)
                .params(controlRequest.getParams())
                .build());
        RvcCmdDo rvcCmd = rvcFactory.buildCmd(vin, tboxCmd.getCmdId(), RemoteControlType.FIND_VEHICLE, controlRequest.getParams(), AccountType.USER, clientAccount.getUid());
        // TODO 后期加省市区
        vehicleRvcDo.findVehicle(rvcCmd);
        vehicleRvcRepository.save(vehicleRvcDo);
        return ControlResponseAssembler.INSTANCE.fromDo(rvcCmd);
    }

}
