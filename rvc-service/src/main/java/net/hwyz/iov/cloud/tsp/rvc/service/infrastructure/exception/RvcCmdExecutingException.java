package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;

/**
 * 车辆远控指令正在执行中异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class RvcCmdExecutingException extends RvcBaseException {

    private static final int ERROR_CODE = 301001;

    public RvcCmdExecutingException(String vin, RemoteControlType rvcCmdType) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]指令[{}]正在执行中", vin, rvcCmdType);
    }

}
