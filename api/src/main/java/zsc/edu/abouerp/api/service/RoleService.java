package zsc.edu.abouerp.api.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.api.domain.QRole;
import zsc.edu.abouerp.api.domain.Role;
import zsc.edu.abouerp.api.repository.RoleRepository;
import zsc.edu.abouerp.api.vo.RoleVO;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    public Page<Role> findAll(RoleVO roleVO, Pageable pageable) {
        if (roleVO == null) {
            return roleRepository.findAll(pageable);
        }
        QRole qRole = QRole.role;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (roleVO.getName() != null && !roleVO.getName().isEmpty()) {
            booleanBuilder.and(qRole.name.containsIgnoreCase(roleVO.getName()));
        }
        return roleRepository.findAll(booleanBuilder, pageable);
    }

    public List<Role> findByIdIn(List<Integer> ids) {
        return roleRepository.findByIdIn(ids);
    }
}
