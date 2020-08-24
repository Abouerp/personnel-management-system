package zsc.edu.abouerp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zsc.edu.abouerp.entity.domain.ResignMessage;

/**
 * @author Abouerp
 */
@Repository
public interface ResignMessageRepository extends JpaRepository<ResignMessage, Integer> {
}
