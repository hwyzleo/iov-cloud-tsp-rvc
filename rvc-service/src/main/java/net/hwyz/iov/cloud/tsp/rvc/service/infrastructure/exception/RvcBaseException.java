package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception;

/**
 * 远程车辆控制服务基础异常
 *
 * @author hwyz_leo
 */
public class RvcBaseException extends RuntimeException {

    private static final int ERROR_CODE = 301000;

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误消息
     */
    private String message;

    public RvcBaseException(String message) {
        this.code = ERROR_CODE;
        this.message = message;
    }

    public RvcBaseException(int errorCode, String message) {
        this.code = errorCode;
        this.message = message;
    }

}
