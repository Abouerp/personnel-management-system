package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.entity.domain.QStation;
import zsc.edu.abouerp.entity.domain.Station;
import zsc.edu.abouerp.entity.vo.StationVO;
import zsc.edu.abouerp.service.repository.StationRepository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station save(Station station) {
        return stationRepository.save(station);
    }

    public void delete(Integer id) {
        stationRepository.deleteById(id);
    }

    public Optional<Station> findById(Integer id) {
        return stationRepository.findById(id);
    }

    public Page<Station> findAll(StationVO stationVO, Pageable pageable) {
        if (stationVO == null) {
            return stationRepository.findAll(pageable);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QStation qStation = QStation.station;
        if (stationVO.getName() != null && !stationVO.getName().isEmpty()) {
            booleanBuilder.and(qStation.name.containsIgnoreCase(stationVO.getName()));
        }
        if (stationVO.getDepartmentId() != null) {
            booleanBuilder.and(qStation.department.id.eq(stationVO.getDepartmentId()));
        }
        if (stationVO.getDescription() != null && !stationVO.getDescription().isEmpty()) {
            booleanBuilder.and(qStation.description.containsIgnoreCase(stationVO.getDescription()));
        }
        if (stationVO.getNumber() != null && !stationVO.getNumber().isEmpty()) {
            booleanBuilder.and(qStation.number.containsIgnoreCase(stationVO.getNumber()));
        }
        return stationRepository.findAll(booleanBuilder, pageable);
    }

}
