package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.external;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service.ExPushService;
import net.hwyz.iov.cloud.tsp.umr.api.contract.request.SingleMobilePushRequest;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * 外部推送业务服务回退处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
public class ExPushServiceFallbackFactory implements FallbackFactory<ExPushService> {

    @Override
    public ExPushService create(Throwable cause) {
        return new ExPushService() {

            @Override
            public void pushMobile(SingleMobilePushRequest request) {
                if (logger.isDebugEnabled()) {
                    logger.warn("向单个手机端推送消息异常", cause);
                } else {
                    logger.warn("向单个手机端推送消息异常:[{}]", cause.getMessage());
                }
            }

        };
    }

}
