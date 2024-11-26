package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.po.CmdRecordPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 远控命令记录表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-08-26
 */
@Mapper
public interface CmdRecordDao extends BaseDao<CmdRecordPo, Long> {

    /**
     * 通过指令ID查询指令记录
     *
     * @param cmdId 指令ID
     * @return 指令记录
     */
    CmdRecordPo selectPoByCmdId(String cmdId);

}
