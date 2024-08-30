package net.hwyz.iov.cloud.tsp.rvc.api.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;

/**
 * 远控响应
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlResponse {

    /**
     * 车架号
     */
    private String vin;
    /**
     * 远控请求ID
     */
    private String reqId;
    /**
     * 远控请求状态
     */
    private RvcCmdState reqState;

}