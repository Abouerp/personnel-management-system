package zsc.edu.abouerp.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
public class DepartmentStatisticsDTO {

    private String departmentName;
    private long outPerson;
    private long inPerson;
}
