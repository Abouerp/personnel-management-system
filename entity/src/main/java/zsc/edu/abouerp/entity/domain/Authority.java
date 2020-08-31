package zsc.edu.abouerp.entity.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.EnumMap;

/**
 * @author Abouerp
 */
public enum Authority {

    ROLE_CREATE("角色创建"),
    ROLE_READ("角色查看"),
    ROLE_UPDATE("角色更新"),
    ROLE_DELETE("角色删除"),

    USER_CREATE("用户创建"),
    USER_READ("用户查看"),
    USER_UPDATE("用户更新"),
    USER_DELETE("用户删除"),

    TITLE_CREATE("职称创建"),
    TITLE_READ("职称查看"),
    TITLE_UPDATE("职称更新"),
    TITLE_DELETE("职称删除"),

    DEPARTMENT_CREATE("部门创建"),
    DEPARTMENT_READ("部门查看"),
    DEPARTMENT_UPDATE("部门更新"),
    DEPARTMENT_DELETE("部门删除"),

    ROLE_CHANGE_LOGGER("岗位日志查看"),


    AUTHORITY_READ("权限查看");

    public static final EnumMap<Authority, String> mappings = new EnumMap<>(Authority.class);

    static {
        for (Authority authority : values()) {
            mappings.put(authority, authority.getDescription());
        }
    }

    private final String description;

    Authority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public SimpleGrantedAuthority springAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}
