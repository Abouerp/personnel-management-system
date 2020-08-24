package zsc.edu.abouerp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import zsc.edu.abouerp.entity.domain.RoleChangeLogger;

/**
 * @author Abouerp
 */
@Repository
public interface RoleChangeLoggerRepository extends JpaRepository<RoleChangeLogger,Integer>, QuerydslPredicateExecutor<RoleChangeLogger> {
}
