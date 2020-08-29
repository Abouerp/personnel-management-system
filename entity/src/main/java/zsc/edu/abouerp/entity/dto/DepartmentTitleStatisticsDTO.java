package zsc.edu.abouerp.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
public class DepartmentTitleStatisticsDTO {

    private String departmentName;
    private long low;
    private long medium;
    private long high;
}
