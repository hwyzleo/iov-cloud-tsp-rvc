package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.assembler;

import cn.hutool.json.JSONUtil;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.po.CmdRecordPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 远控命令记录数据对象转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface CmdRecordPoAssembler {

    CmdRecordPoAssembler INSTANCE = Mappers.getMapper(CmdRecordPoAssembler.class);

    /**
     * 数据对象转领域对象
     *
     * @param cmdRecordPo 数据对象
     * @return 领域对象
     */
    @Mappings({
            @Mapping(target = "type", source = "cmdType"),
            @Mapping(target = "params", expression = "java(net.hwyz.iov.cloud.tsp.framework.commons.util.AssemblerHelper.json2Map(cmdRecordPo.getCmdParam()))"),
            @Mapping(target = "cmdState", expression = "java(net.hwyz.iov.cloud.tsp.rvc.api.contract.enums.RvcCmdState.valOf(cmdRecordPo.getCmdState()))")
    })
    RvcCmdDo toDo(CmdRecordPo cmdRecordPo);

    /**
     * 数据对象列表转领域对象列表
     *
     * @param cmdRecordPoList 数据对象列表
     * @return 领域对象列表
     */
    List<RvcCmdDo> toDoList(List<CmdRecordPo> cmdRecordPoList);

    /**
     * 领域对象转数据对象
     *
     * @param rvcCmdDo 领域对象
     * @return 数据对象
     */
    @Mappings({
            @Mapping(target = "cmdType", source = "type"),
            @Mapping(target = "cmdParam", expression = "java(net.hwyz.iov.cloud.tsp.framework.commons.util.AssemblerHelper.map2Json(rvcCmdDo.getParams()))"),
            @Mapping(target = "cmdState", source = "cmdState.value")
    })
    CmdRecordPo fromDo(RvcCmdDo rvcCmdDo);

}
