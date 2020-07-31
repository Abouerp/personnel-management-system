package zsc.edu.abouerp.entity.dto;

import lombok.Data;
import zsc.edu.abouerp.entity.domain.PersonnelStatus;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.entity.domain.Title;

import java.time.Instant;
import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class AdministratorDTO {

    private Integer id;
    private String username;
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
    private String probationAccess;
    private Instant offerTime;
    private Title title;
    private String md5;
    private Set<Role> roles;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
