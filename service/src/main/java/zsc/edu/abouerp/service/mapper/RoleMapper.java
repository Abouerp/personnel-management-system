package zsc.edu.abouerp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.entity.vo.RoleVO;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleVO roleVO);
}
