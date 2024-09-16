package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception.RvcCmdExecutingException;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 车辆远控领域对象
 *
 * @author hwyz_leo
 */
@Slf4j
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
    private Map<RemoteControlType, RvcCmdDo> currentCmd;
    /**
     * 已完成的远控指令
     */
    private Set<RvcCmdDo> finishedCmd;

    /**
     * 初始化
     */
    public void init(List<RvcCmdDo> rvcCmdDoList) {
        currentCmd = rvcCmdDoList.stream().collect(Collectors.toMap(RvcCmdDo::getType, r -> r));
        finishedCmd = new HashSet<>();
        stateInit();
    }

    /**
     * 检查是否在进行寻车指令
     */
    public RvcCmdDo isFindVehicleExecuting() {
        RvcCmdDo rvcCmdDo = currentCmd.get(RemoteControlType.FIND_VEHICLE);
        if (rvcCmdDo != null && isTimeout(rvcCmdDo)) {
            if (isTimeout(rvcCmdDo)) {
                logger.warn("远控指令[{}]已超时，进行补偿操作", rvcCmdDo.getCmdId());
                finishedCmd.add(rvcCmdDo);
                currentCmd.remove(RemoteControlType.FIND_VEHICLE);
            } else {
                return rvcCmdDo;
            }
        }
        return null;
    }

    /**
     * 寻车
     *
     * @param rvcCmd 远控指令领域对象
     */
    public void findVehicle(RvcCmdDo rvcCmd) {
        if (isFindVehicleExecuting() != null) {
            throw new RvcCmdExecutingException(vin, RemoteControlType.FIND_VEHICLE);
        }
        currentCmd.put(RemoteControlType.FIND_VEHICLE, rvcCmd);
    }

    /**
     * 判断远控指令是否超时
     *
     * @param rvcCmdDo 远控指令领域对象
     * @return true:超时 false:未超时
     */
    private boolean isTimeout(RvcCmdDo rvcCmdDo) {
        return System.currentTimeMillis() - rvcCmdDo.getStartTime().getTime() > rvcCmdDo.getType().getTimeout();
    }

}
