package zsc.edu.abouerp.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.entity.vo.RoleVO;
import zsc.edu.abouerp.service.exception.RoleNotFoundException;
import zsc.edu.abouerp.service.mapper.RoleMapper;
import zsc.edu.abouerp.service.service.RoleService;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
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
        return role;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResultBean<Role> save(@RequestBody RoleVO roleVO) {
        return ResultBean.ok(roleService.save(RoleMapper.INSTANCE.toRole(roleVO)));
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
