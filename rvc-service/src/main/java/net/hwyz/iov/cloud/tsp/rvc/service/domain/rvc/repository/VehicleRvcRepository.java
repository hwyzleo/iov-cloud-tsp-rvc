package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository;

import net.hwyz.iov.cloud.framework.common.domain.BaseRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;

/**
 * 车辆远控领域仓库接口
 *
 * @author hwyz_leo
 */
public interface VehicleRvcRepository extends BaseRepository<String, VehicleRvcDo> {

    /**
     * 根据车架号获取车辆远控领域对象
     *
     * @param vin 车架号
     * @return 车辆远控领域对象
     */
    VehicleRvcDo getOrCreate(String vin);

}
