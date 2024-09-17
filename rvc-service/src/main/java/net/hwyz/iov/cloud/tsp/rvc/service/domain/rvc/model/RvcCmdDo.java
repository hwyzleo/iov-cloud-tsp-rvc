package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.framework.commons.enums.AccountType;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;

import java.util.Date;
import java.util.Map;

/**
 * 远控指令领域对象
 *
 * @author hwyz_leo
 */
@Slf4j
@Getter
@SuperBuilder
public class RvcCmdDo extends BaseDo<Long> implements DomainObj<VehicleRvcDo> {

    /**
     * 车架号
     */
    private String vin;
    /**
     * 指令ID
     */
    private String cmdId;
    /**
     * 指令类型
     */
    private RemoteControlType type;
    /**
     * 指令参数
     */
    private Map<String, Object> params;
    /**
     * 指令状态
     */
    private RvcCmdState cmdState;
    /**
     * 失败代码
     */
    private Integer failureCode;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 账号类型
     */
    private AccountType accountType;
    /**
     * 账号ID
     */
    private String accountId;

    /**
     * 初始化
     */
    public void init() {
        stateInit();
    }

    /**
     * 指令超时
     */
    public void timeout() {
        endTime = new Date();
        cmdState = RvcCmdState.FAILURE;
        // 超时错误码
        failureCode = 1000;
        stateChange();
    }

    /**
     * 指令执行中
     */
    public void executing() {
        if (cmdState == RvcCmdState.CREATED || cmdState == RvcCmdState.SENT) {
            cmdState = RvcCmdState.EXECUTING;
            stateChange();
        } else {
            logger.warn("车辆[{}]指令[{}]已经执行", vin, cmdId);
        }
    }

    /**
     * 指令执行成功
     */
    public void success() {
        if (cmdState != RvcCmdState.SUCCESS && cmdState != RvcCmdState.FAILURE) {
            cmdState = RvcCmdState.SUCCESS;
            stateChange();
        } else {
            logger.warn("车辆[{}]指令[{}]已经执行结束", vin, cmdId);
        }
    }

    /**
     * 指令执行失败
     *
     * @param failureCode 指令错误码
     */
    public void failure(Integer failureCode) {
        if (cmdState != RvcCmdState.SUCCESS && cmdState != RvcCmdState.FAILURE) {
            this.failureCode = failureCode;
            cmdState = RvcCmdState.FAILURE;
            stateChange();
        } else {
            logger.warn("车辆[{}]指令[{}]已经执行结束", vin, cmdId);
        }
    }

}
