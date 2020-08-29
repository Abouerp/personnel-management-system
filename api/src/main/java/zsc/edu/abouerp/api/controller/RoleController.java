package zsc.edu.abouerp.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.entity.vo.RoleVO;
import zsc.edu.abouerp.service.exception.DepartmentNotFoundException;
import zsc.edu.abouerp.service.exception.RoleNotFoundException;
import zsc.edu.abouerp.service.mapper.RoleMapper;
import zsc.edu.abouerp.service.service.DepartmentService;
import zsc.edu.abouerp.service.service.RoleService;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;
    private final DepartmentService departmentService;

    public RoleController(RoleService roleService,
                          DepartmentService departmentService) {
        this.roleService = roleService;
        this.departmentService = departmentService;
    }

    private static Role updateInfo(Role role, RoleVO roleVO) {
        if (roleVO != null && roleVO.getDescription() != null) {
            role.setDescription(roleVO.getDescription());
        }
        if (roleVO != null && roleVO.getName() != null) {
            role.setName(roleVO.getName());
        }
        if (roleVO != null && roleVO.getAuthorities() != null && !roleVO.getAuthorities().isEmpty()) {
            role.setAuthorities(roleVO.getAuthorities());
        }
        if (roleVO != null && roleVO.getBasicSalary() != null) {
            role.setBasicSalary(roleVO.getBasicSalary());
        }
        return role;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResultBean<Role> save(@RequestBody RoleVO roleVO) {
        Role role = RoleMapper.INSTANCE.toRole(roleVO);
        if (roleVO.getDepartmentId() != null) {
            Department department = departmentService.findById(roleVO.getDepartmentId())
                    .orElseThrow(DepartmentNotFoundException::new);
            role.setDepartment(department);
        }
        return ResultBean.ok(roleService.save(role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResultBean<Object> delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return ResultBean.ok();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResultBean<Role> update(@PathVariable Integer id, @RequestBody RoleVO roleVO) {
        Role role = roleService.findById(id).orElseThrow(RoleNotFoundException::new);
        if (roleVO.getDepartmentId() != null) {
            Department department = departmentService.findById(roleVO.getDepartmentId())
                    .orElseThrow(DepartmentNotFoundException::new);
            role.setDepartment(department);
        }
        return ResultBean.ok(roleService.save(updateInfo(role, roleVO)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResultBean<Page<Role>> findAll(
            @PageableDefault Pageable pageable,
            RoleVO roleVO
    ) {
        return ResultBean.ok(roleService.findAll(roleVO, pageable));
    }
}
