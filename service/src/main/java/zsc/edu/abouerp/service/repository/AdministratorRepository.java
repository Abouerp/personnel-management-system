package zsc.edu.abouerp.service.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import zsc.edu.abouerp.entity.domain.Administrator;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>, QuerydslPredicateExecutor<Administrator> {

    @EntityGraph(attributePaths = "roles.authorities")
    Optional<Administrator> findFirstByUsername(String username);

    Integer countByEnabled(Boolean enabled);
}
