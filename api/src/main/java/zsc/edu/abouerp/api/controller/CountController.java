package zsc.edu.abouerp.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.service.service.AdministratorService;
import zsc.edu.abouerp.service.service.DepartmentService;
import zsc.edu.abouerp.service.service.RoleChangeLoggerService;

import java.util.List;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/count")
public class CountController {
    private final AdministratorService administratorService;
    private final RoleChangeLoggerService roleChangeLoggerService;
    private final DepartmentService departmentService;

    public CountController(AdministratorService administratorService,
                           RoleChangeLoggerService roleChangeLoggerService,
                           DepartmentService departmentService) {
        this.administratorService = administratorService;
        this.roleChangeLoggerService = roleChangeLoggerService;
        this.departmentService = departmentService;
    }

    public ResultBean countByDepartment(){
        List<Department> departments = departmentService.findAll();

        return ResultBean.ok();
    }
}
