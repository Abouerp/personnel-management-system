package zsc.edu.abouerp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import zsc.edu.abouerp.entity.domain.Station;
import zsc.edu.abouerp.entity.vo.StationVO;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StationMapper {

    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);

    Station toStation(StationVO stationVO);

}
