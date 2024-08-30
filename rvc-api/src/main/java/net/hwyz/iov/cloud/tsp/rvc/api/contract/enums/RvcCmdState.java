package net.hwyz.iov.cloud.tsp.rvc.api.contract.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 远控指令状态
 *
 * @author hwyz_leo
 */
@AllArgsConstructor
public enum RvcCmdState {

    /** 已创建 **/
    CREATED(0),
    /** 已发送 **/
    SENT(1),
    /** 执行中 **/
    EXECUTING(2),
    /** 成功 **/
    SUCCESS(3),
    /** 失败 **/
    FAILURE(4);

    public final Integer value;

    public static RvcCmdState valOf(Integer val) {
        return Arrays.stream(RvcCmdState.values())
                .filter(rvcCmdState -> rvcCmdState.value.equals(val))
                .findFirst()
                .orElse(null);
    }

}
