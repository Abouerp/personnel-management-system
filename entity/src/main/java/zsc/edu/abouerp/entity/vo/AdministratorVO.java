package zsc.edu.abouerp.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Abouerp
 */
@Data
public class AdministratorVO {
    @NotNull
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String sex;
    @NotNull
    private String realName;
    private Integer collegeId;
    private String jobNumber;
    private List<Integer> role;
    private Boolean startTask;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
}
