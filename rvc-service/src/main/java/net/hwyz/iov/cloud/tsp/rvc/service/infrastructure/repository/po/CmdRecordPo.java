package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.experimental.SuperBuilder;
import lombok.*;
import net.hwyz.iov.cloud.framework.mysql.po.BasePo;

/**
 * <p>
 * 远控命令记录表 数据对象
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-08-26
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_cmd_record")
public class CmdRecordPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车架号
     */
    @TableField("vin")
    private String vin;

    /**
     * 指令ID
     */
    @TableField("cmd_id")
    private String cmdId;

    /**
     * 指令类型
     */
    @TableField("cmd_type")
    private String cmdType;

    /**
     * 指令参数
     */
    @TableField("cmd_param")
    private String cmdParam;

    /**
     * 指令状态
     */
    @TableField("cmd_state")
    private Integer cmdState;

    /**
     * 失败代码
     */
    @TableField("failure_code")
    private Integer failureCode;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 账号类型
     */
    @TableField("account_type")
    private String accountType;

    /**
     * 账号ID
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 省
     */
    @TableField("province")
    private String province;

    /**
     * 市
     */
    @TableField("city")
    private String city;

    /**
     * 区
     */
    @TableField("county")
    private String county;
}
