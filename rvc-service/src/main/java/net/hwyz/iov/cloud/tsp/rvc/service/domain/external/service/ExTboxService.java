package net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service;

import jakarta.validation.Valid;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.external.ExTboxServiceFallbackFactory;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.request.RemoteControlRequest;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.response.TboxCmdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 外部TBOX业务服务
 *
 * @author hwyz_leo
 */
@FeignClient(name = "tbox-service", path = "/service/tbox", fallbackFactory = ExTboxServiceFallbackFactory.class)
public interface ExTboxService {

    /**
     * 远程控制
     *
     * @param request 远控请求
     * @return TBOX指令响应
     */
    @PostMapping("/action/remoteControl")
    TboxCmdResponse remoteControl(@RequestBody @Valid RemoteControlRequest request);

}
