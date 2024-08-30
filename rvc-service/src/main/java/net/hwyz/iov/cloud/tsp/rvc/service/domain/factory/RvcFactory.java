package net.hwyz.iov.cloud.tsp.rvc.service.domain.factory;

import cn.hutool.core.util.IdUtil;
import net.hwyz.iov.cloud.tsp.framework.commons.enums.AccountType;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.contract.enums.RvcCmdType;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.VehicleRvcDo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 远控领域工厂类
 *
 * @author hwyz_leo
 */
@Component
public class RvcFactory {

    /**
     * 创建车辆远控领域对象
     *
     * @param vin 车架号
     * @return 车辆远控领域对象
     */
    public VehicleRvcDo buildVehicle(String vin) {
        return VehicleRvcDo.builder()
                .vin(vin)
                .build();
    }

    /**
     * 创建远控指令领域对象
     *
     * @param vin         车架号
     * @param rvcCmdType  远控指令类型
     * @param params      远控指令参数
     * @param accountType 账号类型
     * @param accountId   账号ID
     * @return 远控指令领域对象
     */
    public RvcCmdDo buildCmd(String vin, RvcCmdType rvcCmdType, Map<String, Object> params, AccountType accountType, String accountId) {
        if (params == null) {
            params = Map.of();
        }
        RvcCmdDo rvcCmdDo = RvcCmdDo.builder()
                .vin(vin)
                .cmdId(IdUtil.simpleUUID())
                .type(rvcCmdType)
                .params(params)
                .cmdState(RvcCmdState.CREATED)
                .startTime(new Date())
                .accountType(accountType)
                .accountId(accountId)
                .build();
        rvcCmdDo.init();
        return rvcCmdDo;
    }

}
