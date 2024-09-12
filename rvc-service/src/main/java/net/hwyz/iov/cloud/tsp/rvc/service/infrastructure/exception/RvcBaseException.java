package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception;

import net.hwyz.iov.cloud.tsp.framework.commons.exception.BaseException;

/**
 * 远程车辆控制服务基础异常
 *
 * @author hwyz_leo
 */
public class RvcBaseException extends BaseException {

    private static final int ERROR_CODE = 301000;

    public RvcBaseException(String message) {
        super(ERROR_CODE, message);
    }

    public RvcBaseException(int errorCode) {
        super(errorCode);
    }

    public RvcBaseException(int errorCode, String message) {
        super(errorCode, message);
    }

}
