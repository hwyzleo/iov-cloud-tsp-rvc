package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
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

}
