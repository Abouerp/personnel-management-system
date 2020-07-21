package zsc.edu.abouerp.entity.vo;

import lombok.Data;
import zsc.edu.abouerp.entity.domain.PersonnelStatus;

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
    private String realName;
    private String mobile;
    private String email;
    private String address;
    private String number;
    private String idCard;
    private String description;
    private Double wage;
    private String sex;
    private PersonnelStatus status;
    private List<Integer> role;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
}
