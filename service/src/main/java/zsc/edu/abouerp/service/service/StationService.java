package zsc.edu.abouerp.service.service;

import org.springframework.stereotype.Service;
import zsc.edu.abouerp.entity.domain.Station;
import zsc.edu.abouerp.service.repository.StationRepository;

/**
 * @author Abouerp
 */
@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station save(Station station){
        return stationRepository.save(station);
    }

    public void delete(Integer id){
        stationRepository.deleteById(id);
    }
}
