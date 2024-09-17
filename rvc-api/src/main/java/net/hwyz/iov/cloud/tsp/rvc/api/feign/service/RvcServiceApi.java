package net.hwyz.iov.cloud.tsp.rvc.api.feign.service;

import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;

/**
 * 远程车辆控制相关服务接口
 *
 * @author hwyz_leo
 */
public interface RvcServiceApi {

    /**
     * 更新远控指令状态
     *
     * @param cmdId    指令ID
     * @param response 远控指令响应
     */
    void updateCmdState(String cmdId, ControlResponse response);

}
