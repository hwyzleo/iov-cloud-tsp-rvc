package net.hwyz.iov.cloud.tsp.rvc.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.ClientAccount;
import net.hwyz.iov.cloud.tsp.framework.commons.enums.AccountType;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.request.ControlRequest;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service.ExPushService;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service.ExTboxService;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.factory.RvcFactory;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository.VehicleRvcRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.service.RvcService;
import net.hwyz.iov.cloud.tsp.rvc.service.facade.assembler.ControlResponseAssembler;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception.RvcCmdNotExistException;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception.RvcTypeNotSupportException;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.util.RvcFailureHelper;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.request.RemoteControlRequest;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.response.TboxCmdResponse;
import net.hwyz.iov.cloud.tsp.umr.api.contract.request.SingleMobilePushRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    private final ExPushService exPushService;
    private final RvcFailureHelper rvcFailureHelper;
    private final VehicleRvcRepository vehicleRvcRepository;

    /**
     * 远控消息推送模板代码
     */
    @Value("${msg-template.rvc:MP_000001}")
    private String rvcMsgTemplateCode;

    /**
     * 寻车
     *
     * @param controlRequest 车控请求
     * @param clientAccount  客户端账号
     * @return 车控返回
     */
    public ControlResponse findVehicle(ControlRequest controlRequest, ClientAccount clientAccount) {
        String vin = controlRequest.getVin();
        logger.info("用户[{}]执行远控：寻车[{}]", clientAccount.getAccountId(), vin);
        VehicleRvcDo vehicleRvcDo = rvcService.getOrCreate(vin);
        RvcCmdDo findVehicleCmd = vehicleRvcDo.isFindVehicleExecuting();
        if (findVehicleCmd == null) {
            TboxCmdResponse tboxCmd = exTboxService.remoteControl(RemoteControlRequest.builder()
                    .vin(vin)
                    .type(RemoteControlType.FIND_VEHICLE)
                    .params(controlRequest.getParams())
                    .build());
            findVehicleCmd = rvcFactory.buildCmd(vin, tboxCmd.getCmdId(), RemoteControlType.FIND_VEHICLE,
                    controlRequest.getParams(), AccountType.USER, clientAccount.getAccountId());
            // TODO 后期加省市区
            vehicleRvcDo.findVehicle(findVehicleCmd);
            vehicleRvcRepository.save(vehicleRvcDo);
        } else {
            logger.warn("用户[{}]执行远控：寻车[{}]，正在执行中", clientAccount.getAccountId(), vin);
        }
        return ControlResponseAssembler.INSTANCE.fromDo(findVehicleCmd);
    }

    /**
     * 获取远控功能状态
     *
     * @param vin  车架号
     * @param type 远控类型
     * @return 远控功能状态
     */
    public ControlResponse getControlFunctionState(String vin, RemoteControlType type) {
        logger.info("获取车辆[{}]远控功能[{}]状态", vin, type);
        VehicleRvcDo vehicleRvcDo = rvcService.getOrCreate(vin);
        RvcCmdDo rvcCmdDo;
        switch (type) {
            case FIND_VEHICLE -> rvcCmdDo = vehicleRvcDo.isFindVehicleExecuting();
            default -> throw new RvcTypeNotSupportException(vin, type);
        }
        if (rvcCmdDo != null) {
            ControlResponse controlResponse = ControlResponseAssembler.INSTANCE.fromDo(rvcCmdDo);
            if (controlResponse.getCmdState() == RvcCmdState.FAILURE) {
                controlResponse.setFailureMsg(rvcFailureHelper.getMessage(controlResponse.getFailureCode()));
            }
            return controlResponse;
        }
        return null;
    }

    /**
     * 获取远控指令状态
     *
     * @param vin   车架号
     * @param cmdId 指令ID
     * @return 远控指令状态
     */
    public ControlResponse getCmdState(String vin, String cmdId) {
        logger.info("获取车辆[{}]远控指令[{}]状态", vin, cmdId);
        VehicleRvcDo vehicleRvcDo = rvcService.getOrCreate(vin);
        RvcCmdDo rvcCmdDo = vehicleRvcDo.getCmd(cmdId);
        vehicleRvcRepository.save(vehicleRvcDo);
        if (rvcCmdDo != null) {
            ControlResponse controlResponse = ControlResponseAssembler.INSTANCE.fromDo(rvcCmdDo);
            if (controlResponse.getCmdState() == RvcCmdState.FAILURE) {
                controlResponse.setFailureMsg(rvcFailureHelper.getMessage(controlResponse.getFailureCode()));
            }
            return controlResponse;
        }
        throw new RvcCmdNotExistException(vin, cmdId);
    }

    /**
     * 更新远控指令状态
     *
     * @param vin         车架号
     * @param cmdId       指令ID
     * @param cmdState    指令状态
     * @param failureCode 指令错误码
     */
    public void updateCmdState(String vin, String cmdId, RvcCmdState cmdState, Integer failureCode) {
        logger.info("更新车辆[{}]远控指令[{}]状态[{}]", vin, cmdId, cmdState);
        VehicleRvcDo vehicleRvcDo = rvcService.getOrCreate(vin);
        vehicleRvcDo.updateCmd(cmdId, cmdState, failureCode);
        vehicleRvcRepository.save(vehicleRvcDo);
        RvcCmdDo rvcCmdDo = vehicleRvcDo.getCmd(cmdId);
        Map<String, Object> extras = new HashMap<>(3);
        extras.put("cmdId", cmdId);
        extras.put("cmdState", cmdState.name());
        if (cmdState == RvcCmdState.FAILURE) {
            extras.put("failureMsg", rvcFailureHelper.getMessage(failureCode));
        }
        exPushService.pushMobile(SingleMobilePushRequest.builder()
                .accountId(rvcCmdDo.getAccountId())
                .msgTemplateCode(rvcMsgTemplateCode)
                .extras(extras)
                .build());
    }

}
