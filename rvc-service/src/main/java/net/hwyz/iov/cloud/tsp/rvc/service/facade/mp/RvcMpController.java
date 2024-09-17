package net.hwyz.iov.cloud.tsp.rvc.service.facade.mp;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.ClientAccount;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.Response;
import net.hwyz.iov.cloud.tsp.framework.commons.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.request.ControlRequest;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;
import net.hwyz.iov.cloud.tsp.rvc.api.feign.mp.RvcMpApi;
import net.hwyz.iov.cloud.tsp.rvc.service.application.service.RvcAppService;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 远程车辆控制相关手机接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mp/rvc")
public class RvcMpController implements RvcMpApi {

    private final RvcAppService rvcAppService;

    /**
     * 寻车
     *
     * @param controlRequest 车控请求
     * @param clientAccount  终端用户
     * @return 操作结果
     */
    @Override
    @PostMapping("/action/findVehicle")
    public Response<ControlResponse> findVehicle(@RequestBody @Valid ControlRequest controlRequest,
                                                 @RequestHeader ClientAccount clientAccount) {
        Assert.isTrue(StrUtil.isNotBlank(clientAccount.getUid()), "用户ID不能为空");
        logger.info("手机客户端[{}]对车辆[{}]进行远程寻车", ParamHelper.getClientAccountInfo(clientAccount), controlRequest.getVin());
        return new Response<>("寻车操作成功", rvcAppService.findVehicle(controlRequest, clientAccount));
    }

    /**
     * 获取寻车状态
     *
     * @param vin           车架号
     * @param clientAccount 终端用户
     * @return 操作结果
     */
    @Override
    @GetMapping("/findVehicle")
    public Response<ControlResponse> getFindVehicleState(@RequestParam String vin, @RequestHeader ClientAccount clientAccount) {
        Assert.isTrue(StrUtil.isNotBlank(clientAccount.getUid()), "用户ID不能为空");
        logger.info("手机客户端[{}]获取车辆[{}]远程寻车状态", ParamHelper.getClientAccountInfo(clientAccount), vin);
        return new Response<>("获取寻车状态成功", rvcAppService.getControlFunctionState(vin, RemoteControlType.FIND_VEHICLE));
    }

    /**
     * 获取指令状态
     *
     * @param vin           车架号
     * @param cmdId         指令ID
     * @param clientAccount 终端用户
     * @return 指令状态
     */
    @Override
    @GetMapping("/cmd")
    public Response<ControlResponse> getCmdState(@RequestParam String vin, @RequestParam String cmdId,
                                                 @RequestHeader ClientAccount clientAccount) {
        Assert.isTrue(StrUtil.isNotBlank(clientAccount.getUid()), "用户ID不能为空");
        logger.info("手机客户端[{}]获取车辆[{}]指令[{}]状态", ParamHelper.getClientAccountInfo(clientAccount), vin, cmdId);
        return new Response<>("获取指令状态成功", rvcAppService.getCmdState(vin, cmdId));
    }

}
