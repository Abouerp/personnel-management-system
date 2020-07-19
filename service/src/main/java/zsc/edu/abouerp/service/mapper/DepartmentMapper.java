package zsc.edu.abouerp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import zsc.edu.abouerp.entity.domain.Department;
import zsc.edu.abouerp.entity.vo.DepartmentVO;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    Department toDepartment(DepartmentVO departmentVO);
}
