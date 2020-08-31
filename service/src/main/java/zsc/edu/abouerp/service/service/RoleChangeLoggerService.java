package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import zsc.edu.abouerp.entity.domain.QRoleChangeLogger;
import zsc.edu.abouerp.entity.domain.RoleChangeLogger;
import zsc.edu.abouerp.service.repository.RoleChangeLoggerRepository;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Service
public class RoleChangeLoggerService {
    private final RoleChangeLoggerRepository changeLoggerRepository;

    public RoleChangeLoggerService(RoleChangeLoggerRepository changeLoggerRepository) {
        this.changeLoggerRepository = changeLoggerRepository;
    }

    public Page<RoleChangeLogger> findAll(Pageable pageable, RoleChangeLogger changeLogger) {
        if (changeLogger == null) {
            return changeLoggerRepository.findAll(pageable);
        }
        QRoleChangeLogger qRoleChangeLogger = QRoleChangeLogger.roleChangeLogger;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (changeLogger.getRealName() != null && !changeLogger.getRealName().isEmpty()) {
            booleanBuilder.and(qRoleChangeLogger.realName.containsIgnoreCase(changeLogger.getRealName()));
        }
        if (changeLogger.getBeforeRoleName() != null && !changeLogger.getBeforeRoleName().isEmpty()) {
            booleanBuilder.and(qRoleChangeLogger.beforeRoleName.containsIgnoreCase(changeLogger.getBeforeRoleName()));
        }
        if (changeLogger.getAfterRoleName() != null && !changeLogger.getAfterRoleName().isEmpty()) {
            booleanBuilder.and((qRoleChangeLogger.afterRoleName.containsIgnoreCase(changeLogger.getAfterRoleName())));
        }
        return changeLoggerRepository.findAll(booleanBuilder, pageable);
    }

    public RoleChangeLogger save(RoleChangeLogger roleChangeLogger) {
        return changeLoggerRepository.save(roleChangeLogger);
    }

    public long findByBeforeDepartmentId(Integer id, Instant startTime, Instant endTime) {
        //查找离开此部门的人
        return changeLoggerRepository.findByBeforeDepartmentId(id).stream().filter(
                it -> it.getCreateTime().getEpochSecond() >= startTime.getEpochSecond() &&
                        it.getCreateTime().getEpochSecond() <= endTime.getEpochSecond())
                .count();
    }

    public long findByAfterDepartmentId(Integer id, Instant startTime, Instant endTime) {
        //查找调入此部门的人
        return changeLoggerRepository.findByAfterDepartmentId(id).stream().filter(
                it -> it.getResign() == false &&
                        it.getCreateTime().getEpochSecond() >= startTime.getEpochSecond() &&
                        it.getCreateTime().getEpochSecond() <= endTime.getEpochSecond())
                .count();
    }

    public long findByInDepartment(Integer id, Instant startTime, Instant endTime) {
        //部门新近人数
        return changeLoggerRepository.findByAfterDepartmentId(id).stream().filter(it -> it.getBeforeDepartmentId() == it.getAfterDepartmentId() &&
                it.getResign() == false &&
                it.getCreateTime().getEpochSecond() >= startTime.getEpochSecond() &&
                it.getCreateTime().getEpochSecond() <= endTime.getEpochSecond())
                .count();
    }

    public long findByOutDepartment(Integer id, Instant startTime, Instant endTime) {
        //部门离职人数
        return changeLoggerRepository.findByBeforeDepartmentId(id).stream().filter(it -> it.getResign() == true &&
                it.getCreateTime().getEpochSecond() >= startTime.getEpochSecond() &&
                it.getCreateTime().getEpochSecond() <= endTime.getEpochSecond())
                .count();
    }
}
