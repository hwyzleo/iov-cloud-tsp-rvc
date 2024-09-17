package net.hwyz.iov.cloud.tsp.rvc.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;
import net.hwyz.iov.cloud.tsp.rvc.api.feign.service.RvcServiceApi;
import net.hwyz.iov.cloud.tsp.rvc.service.application.service.RvcAppService;
import org.springframework.web.bind.annotation.*;

/**
 * 远程车辆控制相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/rvc")
public class RvcServiceController implements RvcServiceApi {

    private final RvcAppService rvcAppService;

    /**
     * 更新远控指令状态
     *
     * @param response 远控指令响应
     */
    @Override
    @PostMapping("/cmd/{cmdId}")
    public void updateCmdState(@PathVariable("cmdId") String cmdId, @RequestBody ControlResponse response) {
        logger.info("更新远控指令[{}]状态", cmdId);
        rvcAppService.updateCmdState(response.getVin(), cmdId, response.getCmdState(), response.getFailureCode());
    }

}
