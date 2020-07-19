package zsc.edu.abouerp.entity.dto;

import lombok.Data;
import zsc.edu.abouerp.entity.domain.Role;

import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class AdministratorDTO {

    private Integer id;
    private String username;
    private String mobile;
    private String email;
    private String address;
    private String number;
    private String idCard;
    private String description;
    private Double wage;

    private Set<Role> roles;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
