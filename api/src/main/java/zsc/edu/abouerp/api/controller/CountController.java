package zsc.edu.abouerp.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.entity.dto.DepartmentStatisticsDTO;
import zsc.edu.abouerp.service.service.AdministratorService;
import zsc.edu.abouerp.service.service.DepartmentService;
import zsc.edu.abouerp.service.service.RoleChangeLoggerService;

import java.time.Instant;
import java.util.ArrayList;
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

    @GetMapping("/out-in-department")
    public ResultBean<List<DepartmentStatisticsDTO>> countByDepartment(Instant startTime, Instant endTime) {
        List<DepartmentStatisticsDTO> departmentStatisticsDTOS = new ArrayList<>();
        List<Department> departments = departmentService.findAll();
        for (Department department : departments) {
            long outPerson = roleChangeLoggerService.findByBeforeDepartmentId(department.getId(), startTime, endTime);
            long inPerson = roleChangeLoggerService.findByAfterDepartmentId(department.getId(), startTime, endTime);
            DepartmentStatisticsDTO departmentStatisticsDTO = new DepartmentStatisticsDTO()
                    .setDepartmentName(department.getName())
                    .setOutPerson(outPerson)
                    .setInPerson(inPerson);
            departmentStatisticsDTOS.add(departmentStatisticsDTO);
        }
        return ResultBean.ok(departmentStatisticsDTOS);
    }

    @GetMapping("/dss")
    public ResultBean<List<DepartmentStatisticsDTO>> out_in_offerDepartment(Instant startTime, Instant endTime){
        List<DepartmentStatisticsDTO> departmentStatisticsDTOS = new ArrayList<>();
        List<Department> departments = departmentService.findAll();
        for (Department department : departments) {
            long inPerson = roleChangeLoggerService.findByInDepartment(department.getId(), startTime, endTime);
            long outPerson = roleChangeLoggerService.findByOutDepartment(department.getId(),startTime, endTime);
            departmentStatisticsDTOS.add(
                    new DepartmentStatisticsDTO()
                            .setDepartmentName(department.getName())
                            .setInPerson(inPerson).setOutPerson(outPerson));
        }
        return ResultBean.ok(departmentStatisticsDTOS);
    }
}
