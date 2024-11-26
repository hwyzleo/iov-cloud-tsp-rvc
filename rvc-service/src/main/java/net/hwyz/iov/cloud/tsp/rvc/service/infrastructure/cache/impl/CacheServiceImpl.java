package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.cache.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.enums.AccountType;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.factory.RvcFactory;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.cache.CacheService;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 缓存服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RvcFactory rvcFactory;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis Key前缀：车辆远控
     */
    private static final String REDIS_KEY_PREFIX_VEHICLE_RVC = "rvc:veh-rvc:";

    @Override
    public Optional<VehicleRvcDo> getVehicleRvc(String vin) {
        String vehicleRvcDoJson = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX_VEHICLE_RVC + vin);
        if (StrUtil.isNotBlank(vehicleRvcDoJson)) {
            JSONObject jsonObject = JSONUtil.parseObj(vehicleRvcDoJson);
            VehicleRvcDo vehicleRvcDo = rvcFactory.buildVehicle(vin);
            String cmdJson = jsonObject.getStr("cmdMap");
            Map<String, JSONObject> cmdMap = JSONUtil.toBean(cmdJson, new TypeReference<>() {
            }, true);
            List<RvcCmdDo> rvcCmdList = new ArrayList<>();
            cmdMap.values().forEach(rvcCmd -> {
                Date startTime = rvcCmd.getDate("startTime");
                // 只保留24小时内指令
                if (System.currentTimeMillis() - startTime.getTime() <= 24 * 60 * 60 * 1000) {
                    RvcCmdDo rvcCmdDo = RvcCmdDo.builder()
                            .id(rvcCmd.getLong("id"))
                            .vin(rvcCmd.getStr("vin"))
                            .cmdId(rvcCmd.getStr("cmdId"))
                            .type(RemoteControlType.valueOf(rvcCmd.getStr("type")))
                            .params(rvcCmd.getJSONObject("params").toBean(new TypeReference<>() {
                            }))
                            .cmdState(RvcCmdState.valueOf(rvcCmd.getStr("cmdState")))
                            .failureCode(rvcCmd.getInt("failureCode"))
                            .startTime(rvcCmd.getDate("startTime"))
                            .endTime(rvcCmd.getDate("endTime"))
                            .accountType(AccountType.valueOf(rvcCmd.getStr("accountType")))
                            .accountId(rvcCmd.getStr("accountId"))
                            .build();
                    rvcCmdDo.stateLoad();
                    rvcCmdList.add(rvcCmdDo);
                }
            });
            vehicleRvcDo.init(rvcCmdList);
            return Optional.of(vehicleRvcDo);
        }
        return Optional.empty();
    }

    @Override
    public void setVehicleRvc(VehicleRvcDo vehicleRvcDo) {
        logger.debug("更新车辆[{}]远控领域对象缓存", vehicleRvcDo.getVin());
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX_VEHICLE_RVC + vehicleRvcDo.getVin(),
                JSONUtil.parse(vehicleRvcDo).toJSONString(0));
    }

}
