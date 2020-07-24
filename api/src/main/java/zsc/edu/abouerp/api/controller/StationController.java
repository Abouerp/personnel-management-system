package zsc.edu.abouerp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.entity.domain.Station;
import zsc.edu.abouerp.entity.vo.StationVO;
import zsc.edu.abouerp.service.exception.DepartmentNotFoundException;
import zsc.edu.abouerp.service.mapper.StationMapper;
import zsc.edu.abouerp.service.service.DepartmentService;
import zsc.edu.abouerp.service.service.StationService;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/station")
public class StationController {

    private final StationService stationService;
    private final DepartmentService departmentService;

    public StationController(StationService stationService,
                             DepartmentService departmentService) {
        this.stationService = stationService;
        this.departmentService = departmentService;
    }

    private static Station update(Station station, StationVO stationVO) {
        if (stationVO != null && stationVO.getName() != null) {
            station.setName(stationVO.getName());
        }
        if (stationVO != null && stationVO.getDescription() != null) {
            station.setDescription(stationVO.getDescription());
        }
        if (stationVO != null && stationVO.getNumber() != null) {
            station.setNumber(stationVO.getNumber());
        }
        return station;
    }

    @PostMapping
    public ResultBean<Station> save(@RequestBody StationVO stationVO){
        Department department = null;
        if (stationVO.getDepartmentId()!=null){
            department = departmentService.findById(stationVO.getDepartmentId())
                    .orElseThrow(DepartmentNotFoundException::new);
        }
        Station station = StationMapper.INSTANCE.toStation(stationVO);
        station.setDepartment(department);
        return ResultBean.ok(stationService.save(station));
    }
}
