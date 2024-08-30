package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.service;

import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;

/**
 * 远程车辆控制领域服务接口
 *
 * @author hwyz_leo
 */
public interface RvcService {

    /**
     * 获取或新建车辆远控领域对象
     *
     * @param vin 车架号
     * @return 车辆远控领域对象
     */
    VehicleRvcDo getOrCreate(String vin);

}
