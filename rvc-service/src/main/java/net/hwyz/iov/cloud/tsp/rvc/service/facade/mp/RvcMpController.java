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
        logger.info("手机客户端[{}]寻车[{}]", ParamHelper.getClientAccountInfo(clientAccount), controlRequest.getVin());
        return new Response<>(rvcAppService.findVehicle(controlRequest, clientAccount));
    }

}
