package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.po.CmdRecordPo;
import net.hwyz.iov.cloud.tsp.framework.mysql.dao.BaseDao;
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

}
