package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 远控失败信息辅助类
 *
 * @author hwyz_leo
 */
@Slf4j
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "rvc-failure")
public class RvcFailureHelper {

    private Map<Integer, String> message;

    public void setMessage(Map<Integer, String> message) {
        this.message = message;
    }

    public String getMessage(Integer code) {
        return message.getOrDefault(code, "未知错误");
    }

}
