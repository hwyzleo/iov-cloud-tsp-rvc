package net.hwyz.iov.cloud.tsp.rvc.api.feign.mp;

import net.hwyz.iov.cloud.tsp.framework.commons.bean.ClientAccount;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.Response;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.request.ControlRequest;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;

/**
 * 远程车辆控制相关手机接口
 *
 * @author hwyz_leo
 */
public interface RvcMpApi {

    /**
     * 寻车
     *
     * @param controlRequest 车控请求
     * @param clientAccount  终端用户
     * @return 操作结果
     */
    Response<ControlResponse> findVehicle(ControlRequest controlRequest, ClientAccount clientAccount);

}
