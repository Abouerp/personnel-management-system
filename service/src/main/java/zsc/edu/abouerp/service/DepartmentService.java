package zsc.edu.abouerp.api.service;

import org.springframework.stereotype.Service;
import zsc.edu.abouerp.api.domain.Department;
import zsc.edu.abouerp.api.repository.DepartmentRepository;

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
}
