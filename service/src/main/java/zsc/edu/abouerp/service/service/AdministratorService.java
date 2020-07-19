package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zsc.edu.abouerp.entity.domain.Administrator;
import zsc.edu.abouerp.entity.domain.QAdministrator;
import zsc.edu.abouerp.service.repository.AdministratorRepository;
import zsc.edu.abouerp.service.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author techial
 */
@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final RoleRepository roleRepository;

    public AdministratorService(
            AdministratorRepository administratorRepository,
            RoleRepository roleRepository
    ) {
        this.administratorRepository = administratorRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<Administrator> findById(Integer id) {
        return administratorRepository.findById(id);
    }

    @Transactional
    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public long count() {
        return administratorRepository.count();
    }

    public Optional<Administrator> findFirstByUsername(String username) {
        return administratorRepository.findFirstByUsername(username);
    }

    public Page<Administrator> findAll(Administrator administrator, Pageable pageable) {
        QAdministrator qAdministrator = QAdministrator.administrator;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (administrator != null && administrator.getUsername() != null && !administrator.getUsername().isEmpty()) {
            booleanBuilder.and(qAdministrator.username.containsIgnoreCase(administrator.getUsername()));
        }
        if (administrator != null && administrator.getEmail() != null && !administrator.getEmail().isEmpty()) {
            booleanBuilder.and(qAdministrator.email.containsIgnoreCase(administrator.getEmail()));
        }
        if (administrator != null && administrator.getMobile() != null && !administrator.getMobile().isEmpty()) {
            booleanBuilder.and(qAdministrator.mobile.containsIgnoreCase(administrator.getMobile()));
        }
        if (administrator != null && administrator.getEnabled() != null) {
            booleanBuilder.and(qAdministrator.enabled.eq(administrator.getEnabled()));
        }
        return administratorRepository.findAll(booleanBuilder, pageable);
    }

    public Administrator updateRoles(Integer id, List<Integer> roles) {
        Administrator admin = administratorRepository.getOne(id)
                                                     .setRoles(new HashSet<>(roleRepository.findByIdIn(roles)));
        return administratorRepository.save(admin);
    }

    public void deleteById(Integer id) {
        administratorRepository.deleteById(id);
    }
}