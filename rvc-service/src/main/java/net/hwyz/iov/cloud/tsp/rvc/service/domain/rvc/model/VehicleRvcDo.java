package net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.exception.RvcCmdExecutingException;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.enums.RemoteControlType;

import java.util.*;

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
     * 格式：{远控指令类型:远控指令ID}
     */
    private Map<RemoteControlType, String> currentCmdMap;
    /**
     * 已完成的远控指令
     */
    private Set<String> finishedCmdSet;
    /**
     * 所有远控指令
     * 格式：{远控指令ID:远控指令领域对象}
     */
    private Map<String, RvcCmdDo> cmdMap;


    /**
     * 初始化
     */
    public void init(List<RvcCmdDo> rvcCmdDoList) {
        HashMap<RemoteControlType, String> currentCmdMapTmp = new HashMap<>();
        HashSet<String> finishedCmdSetTmp = new HashSet<>();
        HashMap<String, RvcCmdDo> cmdMapTmp = new HashMap<>();
        for (RvcCmdDo rvcCmdDo : rvcCmdDoList) {
            switch (rvcCmdDo.getCmdState()) {
                case CREATED, SENT, EXECUTING -> currentCmdMapTmp.put(rvcCmdDo.getType(), rvcCmdDo.getCmdId());
                case SUCCESS, FAILURE -> finishedCmdSetTmp.add(rvcCmdDo.getCmdId());
                default -> {
                }
            }
            rvcCmdDo.stateLoad();
            cmdMapTmp.put(rvcCmdDo.getCmdId(), rvcCmdDo);
        }
        currentCmdMap = currentCmdMapTmp;
        finishedCmdSet = finishedCmdSetTmp;
        cmdMap = cmdMapTmp;
        stateInit();
    }

    /**
     * 检查是否在进行寻车指令
     */
    public RvcCmdDo isFindVehicleExecuting() {
        String cmdId = currentCmdMap.get(RemoteControlType.FIND_VEHICLE);
        if (cmdId != null) {
            RvcCmdDo rvcCmdDo = getCmd(cmdId);
            if (currentCmdMap.containsKey(RemoteControlType.FIND_VEHICLE)) {
                return rvcCmdDo;
            }
        }
        return null;
    }

    /**
     * 获取远控指令
     *
     * @param cmdId 远控指令ID
     * @return 远控指令领域对象
     */
    public RvcCmdDo getCmd(String cmdId) {
        RvcCmdDo rvcCmdDo = cmdMap.get(cmdId);
        if (rvcCmdDo != null) {
            if (isTimeout(rvcCmdDo)) {
                logger.warn("远控指令[{}]已超时，进行补偿操作", rvcCmdDo.getCmdId());
                rvcCmdDo.timeout();
                finishedCmdSet.add(cmdId);
                currentCmdMap.remove(rvcCmdDo.getType());
                stateChange();
            }
            return rvcCmdDo;
        } else {
            logger.error("远控指令[{}]集合不一致", cmdId);
        }
        return null;
    }

    /**
     * 更新远控指令状态
     *
     * @param cmdId       指令ID
     * @param cmdState    指令状态
     * @param failureCode 指令错误码
     */
    public void updateCmd(String cmdId, RvcCmdState cmdState, Integer failureCode) {
        RvcCmdDo rvcCmdDo = getCmd(cmdId);
        if (rvcCmdDo != null) {
            switch (cmdState) {
                case EXECUTING -> rvcCmdDo.executing();
                case SUCCESS -> rvcCmdDo.success();
                case FAILURE -> rvcCmdDo.failure(failureCode);
            }
            finishedCmdSet.add(cmdId);
            currentCmdMap.remove(rvcCmdDo.getType());
            stateChange();
        }
    }

    /**
     * 寻车
     *
     * @param rvcCmdDo 远控指令领域对象
     */
    public void findVehicle(RvcCmdDo rvcCmdDo) {
        if (isFindVehicleExecuting() != null) {
            throw new RvcCmdExecutingException(vin, RemoteControlType.FIND_VEHICLE);
        }
        cmdMap.put(rvcCmdDo.getCmdId(), rvcCmdDo);
        currentCmdMap.put(RemoteControlType.FIND_VEHICLE, rvcCmdDo.getCmdId());
        stateChange();
    }

    /**
     * 判断远控指令是否超时
     *
     * @param rvcCmdDo 远控指令领域对象
     * @return true:超时 false:未超时
     */
    private boolean isTimeout(RvcCmdDo rvcCmdDo) {
        return System.currentTimeMillis() - rvcCmdDo.getStartTime().getTime() > rvcCmdDo.getType().getTimeout() * 1000;
    }

}
