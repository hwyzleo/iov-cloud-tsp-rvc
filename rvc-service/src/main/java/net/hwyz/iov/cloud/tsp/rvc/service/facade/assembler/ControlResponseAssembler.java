package net.hwyz.iov.cloud.tsp.rvc.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.rvc.api.contract.response.ControlResponse;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 远控响应转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ControlResponseAssembler {

    ControlResponseAssembler INSTANCE = Mappers.getMapper(ControlResponseAssembler.class);

    /**
     * 领域对象转数据传输对象
     *
     * @param rvcCmdDo 领域对象
     * @return 数据传输对象
     */
    @Mappings({})
    ControlResponse fromDo(RvcCmdDo rvcCmdDo);

}
