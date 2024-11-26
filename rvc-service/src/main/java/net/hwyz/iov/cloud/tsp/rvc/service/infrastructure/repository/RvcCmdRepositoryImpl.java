package net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.domain.AbstractRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.model.RvcCmdDo;
import net.hwyz.iov.cloud.tsp.rvc.service.domain.rvc.repository.RvcCmdRepository;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.assembler.CmdRecordPoAssembler;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.dao.CmdRecordDao;
import net.hwyz.iov.cloud.tsp.rvc.service.infrastructure.repository.po.CmdRecordPo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 远控指令领域仓库接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RvcCmdRepositoryImpl extends AbstractRepository<Long, RvcCmdDo> implements RvcCmdRepository {

    private final CmdRecordDao cmdRecordDao;

    @Override
    public Optional<RvcCmdDo> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(RvcCmdDo rvcCmdDo) {
        switch (rvcCmdDo.getState()) {
            case NEW -> {
                CmdRecordPo cmdRecordPo = CmdRecordPoAssembler.INSTANCE.fromDo(rvcCmdDo);
                cmdRecordDao.insertPo(cmdRecordPo);
                rvcCmdDo.statePersistent(cmdRecordPo.getId());
            }
            case CHANGED -> cmdRecordDao.updatePo(CmdRecordPoAssembler.INSTANCE.fromDo(rvcCmdDo));
            default -> {
                return false;
            }
        }
        return true;
    }
}
