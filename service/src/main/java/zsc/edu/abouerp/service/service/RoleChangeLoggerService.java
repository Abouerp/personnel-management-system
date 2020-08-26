package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import zsc.edu.abouerp.entity.domain.QRoleChangeLogger;
import zsc.edu.abouerp.entity.domain.RoleChangeLogger;
import zsc.edu.abouerp.service.repository.RoleChangeLoggerRepository;

/**
 * @author Abouerp
 */
@Service
public class RoleChangeLoggerService {
    private final RoleChangeLoggerRepository changeLoggerRepository;

    public RoleChangeLoggerService(RoleChangeLoggerRepository changeLoggerRepository) {
        this.changeLoggerRepository = changeLoggerRepository;
    }

    public Page<RoleChangeLogger> findAll(Pageable pageable, RoleChangeLogger changeLogger){
        if (changeLogger == null){
            return changeLoggerRepository.findAll(pageable);
        }
        QRoleChangeLogger qRoleChangeLogger = QRoleChangeLogger.roleChangeLogger;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (changeLogger.getBeforeRoleName()!=null && !changeLogger.getBeforeRoleName().isEmpty() ){
            booleanBuilder.and(qRoleChangeLogger.beforeRoleName.containsIgnoreCase(changeLogger.getBeforeRoleName()));
        }
        if (changeLogger.getAfterRoleName()!=null && !changeLogger.getAfterRoleName().isEmpty()){
            booleanBuilder.and((qRoleChangeLogger.afterRoleName.containsIgnoreCase(changeLogger.getAfterRoleName())));
        }
        return changeLoggerRepository.findAll(booleanBuilder,pageable);
    }

    public RoleChangeLogger save(RoleChangeLogger roleChangeLogger){
        return changeLoggerRepository.save(roleChangeLogger);
    }
}
