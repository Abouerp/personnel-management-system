package zsc.edu.abouerp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zsc.edu.abouerp.api.domain.Department;
import zsc.edu.abouerp.api.mapper.DepartmentMapper;
import zsc.edu.abouerp.api.service.DepartmentService;
import zsc.edu.abouerp.api.vo.DepartmentVO;
import zsc.edu.abouerp.common.entiry.ResultBean;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    private static Department update(Department department, DepartmentVO departmentVO) {
        if (departmentVO == null) {
            return department;
        }
        if (departmentVO.getName() != null) {
            department.setName(department.getName());
        }
        if (departmentVO.getDescription() != null) {
            department.setDescription(departmentVO.getDescription());
        }
        if (departmentVO.getNumber() != null) {
            department.setNumber(departmentVO.getNumber());
        }
        return department;
    }

    @PostMapping
    public ResultBean<Department> save(@RequestBody DepartmentVO departmentVO) {
        return ResultBean.ok(departmentService.save(DepartmentMapper.INSTANCE.toDepartment(departmentVO)));
    }
}
