package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.cache;

import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;

import java.util.Optional;

/**
 * 缓存服务接口
 *
 * @author hwyz_leo
 */
public interface CacheService {

    /**
     * 获取车辆远控领域对象
     *
     * @param vin 车架号
     * @return 车辆远控领域对象
     */
    Optional<VehicleRvcDo> getVehicleRvc(String vin);

    /**
     * 设置车辆远控领域对象
     *
     * @param vehicleRvcDo 车辆远控领域对象
     */
    void setVehicleRvc(VehicleRvcDo vehicleRvcDo);

}
