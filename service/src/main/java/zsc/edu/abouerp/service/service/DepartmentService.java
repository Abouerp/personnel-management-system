package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.entity.domain.QDepartment;
import zsc.edu.abouerp.entity.vo.DepartmentVO;
import zsc.edu.abouerp.service.repository.DepartmentRepository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public void delete(Integer id) {
        departmentRepository.deleteById(id);
    }

    public Optional<Department> findById(Integer id) {
        return departmentRepository.findById(id);
    }

    public Page<Department> findAll(DepartmentVO departmentVO, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QDepartment qDepartment = QDepartment.department;
        if (departmentVO == null) {
            return departmentRepository.findAll(pageable);
        }
        if (departmentVO.getNumber() != null && !departmentVO.getNumber().isEmpty()) {
            booleanBuilder.and(qDepartment.number.containsIgnoreCase(departmentVO.getNumber()));
        }
        if (departmentVO.getName() != null && !departmentVO.getName().isEmpty()) {
            booleanBuilder.and(qDepartment.name.containsIgnoreCase(departmentVO.getName()));
        }
        if (departmentVO.getDescription() != null && !departmentVO.getDescription().isEmpty()) {
            booleanBuilder.and(qDepartment.description.containsIgnoreCase(departmentVO.getDescription()));
        }
        return departmentRepository.findAll(booleanBuilder, pageable);
    }
}
