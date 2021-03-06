package zsc.edu.abouerp.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.entity.vo.DepartmentVO;
import zsc.edu.abouerp.service.exception.DepartmentNotFoundException;
import zsc.edu.abouerp.service.mapper.DepartmentMapper;
import zsc.edu.abouerp.service.service.DepartmentService;

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
            department.setName(departmentVO.getName());
        }
        if (departmentVO.getDescription() != null) {
            department.setDescription(departmentVO.getDescription());
        }
        return department;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DEPARTMENT_CREATE')")
    public ResultBean<Department> save(@RequestBody DepartmentVO departmentVO) {
        return ResultBean.ok(departmentService.save(DepartmentMapper.INSTANCE.toDepartment(departmentVO)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENT_UPDATE')")
    public ResultBean<Department> update(@PathVariable Integer id, @RequestBody DepartmentVO departmentVO) {
        Department department = departmentService.findById(id).orElseThrow(DepartmentNotFoundException::new);
        return ResultBean.ok(departmentService.save(update(department, departmentVO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENT_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        departmentService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DEPARTMENT_READ')")
    public ResultBean<Page<Department>> findAll(
            @PageableDefault Pageable pageable,
            DepartmentVO departmentVO) {
        return ResultBean.ok(departmentService.findAll(departmentVO, pageable));
    }
}
