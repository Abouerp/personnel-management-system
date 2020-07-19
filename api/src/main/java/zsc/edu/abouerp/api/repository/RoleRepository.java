package zsc.edu.abouerp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import zsc.edu.abouerp.api.domain.Role;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, QuerydslPredicateExecutor<Role> {
    List<Role> findByIdIn(List<Integer> roles);

    Optional<Role> findFirstByName(String name);
}
