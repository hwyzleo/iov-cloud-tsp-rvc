package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 车辆远控指令不存在异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class RvcCmdNotExistException extends RvcBaseException {

    private static final int ERROR_CODE = 301003;

    public RvcCmdNotExistException(String vin, String cmdId) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]远控指令[{}]不存在", vin, cmdId);
    }

}
