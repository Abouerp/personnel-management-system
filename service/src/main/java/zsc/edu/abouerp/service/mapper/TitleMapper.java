package zsc.edu.abouerp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import zsc.edu.abouerp.entity.domain.Title;
import zsc.edu.abouerp.entity.vo.TitleVO;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TitleMapper {
    TitleMapper INSTANCE = Mappers.getMapper(TitleMapper.class);

    Title toTitle(TitleVO titleVO);
}
