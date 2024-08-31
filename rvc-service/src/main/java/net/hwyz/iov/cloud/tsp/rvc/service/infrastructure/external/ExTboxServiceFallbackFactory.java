package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.external;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.external.service.ExTboxService;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.request.RemoteControlRequest;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.response.TboxCmdResponse;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * 外部TBOX业务服务回退处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
public class ExTboxServiceFallbackFactory implements FallbackFactory<ExTboxService> {

    @Override
    public ExTboxService create(Throwable cause) {
        return new ExTboxService() {

            @Override
            public TboxCmdResponse remoteControl(RemoteControlRequest request) {
                if (logger.isDebugEnabled()) {
                    logger.warn("远程控制异常", cause);
                } else {
                    logger.warn("远程控制异常:[{}]", cause.getMessage());
                }
                return null;
            }

        };
    }

}
