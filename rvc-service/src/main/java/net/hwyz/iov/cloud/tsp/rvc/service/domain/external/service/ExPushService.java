package net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service;

import jakarta.validation.Valid;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.external.ExPushServiceFallbackFactory;
import net.hwyz.iov.cloud.tsp.umr.api.contract.request.SingleMobilePushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 外部推送业务服务
 *
 * @author hwyz_leo
 */
@FeignClient(name = "umr-service", path = "/service/push", fallbackFactory = ExPushServiceFallbackFactory.class)
public interface ExPushService {

    /**
     * 向单个手机端推送消息
     *
     * @param request 单个手机推送请求
     */
    @PostMapping("/singleMobile")
    void pushMobile(@RequestBody @Valid SingleMobilePushRequest request);

}
