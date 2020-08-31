package zsc.edu.abouerp.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.RoleChangeLogger;
import zsc.edu.abouerp.service.service.RoleChangeLoggerService;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/logger")
public class LoggerController {

    private final RoleChangeLoggerService roleChangeLoggerService;

    public LoggerController(RoleChangeLoggerService roleChangeLoggerService) {
        this.roleChangeLoggerService = roleChangeLoggerService;
    }

    @GetMapping("/role-change")
    @PreAuthorize("hasAuthority('ROLE_CHANGE_LOGGER')")
    public ResultBean<Page<RoleChangeLogger>> findAll(
            @PageableDefault Pageable pageable,
            RoleChangeLogger changeLogger) {
        return ResultBean.ok(roleChangeLoggerService.findAll(pageable, changeLogger));
    }
}
