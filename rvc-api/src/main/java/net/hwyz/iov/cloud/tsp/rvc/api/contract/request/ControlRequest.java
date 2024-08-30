package net.hwyz.iov.cloud.tsp.rvc.api.contract.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 远控请求
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlRequest {

    /**
     * 车架号
     */
    @NotEmpty(message = "车架号不允许为空")
    private String vin;
    /**
     * 指令参数
     */
    private Map<String, Object> params;

}
