package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.AbstractRepository;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DoState;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.factory.RvcFactory;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository.RvcCmdRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository.VehicleRvcRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.cache.CacheService;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.assembler.CmdRecordPoAssembler;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.dao.CmdRecordDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 车辆远控领域仓库接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class VehicleRvcRepositoryImpl extends AbstractRepository<String, VehicleRvcDo> implements VehicleRvcRepository {

    private final RvcFactory rvcFactory;
    private final CacheService cacheService;
    private final CmdRecordDao cmdRecordDao;
    private final RvcCmdRepository rvcCmdRepository;

    @Override
    public VehicleRvcDo getOrCreate(String vin) {
        return cacheService.getVehicleRvc(vin).orElseGet(() -> {
            logger.info("创建车辆[{}]远控领域对象", vin);
            VehicleRvcDo vehicleRvcDo = rvcFactory.buildVehicle(vin);
            Map<String, Object> map = new HashMap<>();
            map.put("vin", vin);
            // 只查询24小时内指令
            map.put("cmdTimeDayRange", 1);
            vehicleRvcDo.init(CmdRecordPoAssembler.INSTANCE.toDoList(cmdRecordDao.selectPoByMap(map)));
            return vehicleRvcDo;
        });
    }

    @Override
    public Optional<VehicleRvcDo> getById(String vin) {
        return Optional.empty();
    }

    @Override
    public boolean save(VehicleRvcDo vehicleRvcDo) {
        vehicleRvcDo.getCmdMap().values().forEach(rvcCmdDo -> {
            if (rvcCmdDo.getState() != DoState.UNCHANGED) {
                rvcCmdRepository.save(rvcCmdDo);
            }
        });
        if (vehicleRvcDo.getState() != DoState.UNCHANGED) {
            cacheService.setVehicleRvc(vehicleRvcDo);
        }
        return true;
    }

}
