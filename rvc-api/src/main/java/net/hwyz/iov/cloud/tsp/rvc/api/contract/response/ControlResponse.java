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
     * 远控指令ID
     */
    private String cmdId;
    /**
     * 远控指令状态
     */
    private RvcCmdState cmdState;
    /**
     * 远控指令错误码
     */
    private Integer failureCode;
    /**
     * 远控指令错误信息
     */
    private String failureMsg;

}
