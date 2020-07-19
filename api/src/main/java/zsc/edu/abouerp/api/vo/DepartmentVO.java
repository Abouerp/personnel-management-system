package zsc.edu.abouerp.api.vo;

import lombok.Data;

/**
 * @author Abouerp
 */
@Data
public class DepartmentVO {
    private String name;
    private String number;
    private String description;
    private Integer parentId;
}
