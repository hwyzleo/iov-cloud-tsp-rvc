package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.contract.enums.RvcCmdType;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception.RvcCmdExecutingException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 车辆远控领域对象
 *
 * @author hwyz_leo
 */
@Getter
@SuperBuilder
public class VehicleRvcDo extends BaseDo<String> implements DomainObj<VehicleRvcDo> {

    /**
     * 车架号
     */
    private String vin;
    /**
     * 当前进行中的远控指令
     * 格式：{远控指令类型:远控指令领域对象}
     */
    private Map<RvcCmdType, RvcCmdDo> currentCmd;

    /**
     * 初始化
     */
    public void init(List<RvcCmdDo> rvcCmdDoList) {
        currentCmd = rvcCmdDoList.stream().collect(Collectors.toMap(RvcCmdDo::getType, r -> r));
        stateInit();
    }

    /**
     * 寻车
     *
     * @param rvcCmd 远控指令领域对象
     */
    public void findVehicle(RvcCmdDo rvcCmd) {
        if (currentCmd.containsKey(RvcCmdType.FIND_VEHICLE)) {
            throw new RvcCmdExecutingException(vin, RvcCmdType.FIND_VEHICLE);
        }
        currentCmd.put(RvcCmdType.FIND_VEHICLE, rvcCmd);
    }

}
