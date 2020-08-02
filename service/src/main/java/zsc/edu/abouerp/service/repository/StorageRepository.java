package zsc.edu.abouerp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zsc.edu.abouerp.entity.domain.Storage;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

    Optional<Storage> findByMd5(String md5);
}
