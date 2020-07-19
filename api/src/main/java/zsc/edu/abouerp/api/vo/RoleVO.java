package zsc.edu.abouerp.api.vo;

import lombok.Data;
import zsc.edu.abouerp.api.domain.Authority;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class RoleVO {

    private String name;

    private String description;

    private Set<Authority> authorities = new HashSet<>();
}
