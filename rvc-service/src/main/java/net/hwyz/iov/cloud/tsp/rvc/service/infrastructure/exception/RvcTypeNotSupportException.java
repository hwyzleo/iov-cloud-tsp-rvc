package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;

/**
 * 车辆远控类型不支持异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class RvcTypeNotSupportException extends RvcBaseException {

    private static final int ERROR_CODE = 301002;

    public RvcTypeNotSupportException(String vin, RemoteControlType rvcCmdType) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]远控类型[{}]不支持", vin, rvcCmdType);
    }

}
