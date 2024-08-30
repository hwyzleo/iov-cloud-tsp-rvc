package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository.VehicleRvcRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.service.RvcService;
import org.springframework.stereotype.Service;

/**
 * 远程车辆控制领域服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RvcServiceImpl implements RvcService {

    private final VehicleRvcRepository vehicleRvcRepository;

    @Override
    public VehicleRvcDo getOrCreate(String vin) {
        return vehicleRvcRepository.getOrCreate(vin);
    }

}
