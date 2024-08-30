package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception;

import cn.hutool.core.util.StrUtil;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.contract.enums.RvcCmdType;

/**
 * 车辆远控指令正在执行中异常
 *
 * @author hwyz_leo
 */
public class RvcCmdExecutingException extends RvcBaseException {

    private static final int ERROR_CODE = 301001;

    public RvcCmdExecutingException(String vin, RvcCmdType rvcCmdType) {
        super(ERROR_CODE, StrUtil.format("车辆[{}}]指令[{}}]正在执行中", vin, rvcCmdType));
    }

}
