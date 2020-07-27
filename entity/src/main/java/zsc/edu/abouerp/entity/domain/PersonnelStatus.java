package zsc.edu.abouerp.entity.domain;

import java.util.EnumMap;

/**
 * 员工状态
 *
 * @author Abouerp
 */
public enum PersonnelStatus {

    PROBATION("试用期"),

    IN_OFFICE("转正"),

    UN_OFFICE("不录用"),

    OFF_OFFICE("离职");

    private final String description;

    public static final EnumMap<PersonnelStatus, String> mappings = new EnumMap<>(PersonnelStatus.class);

    static {
        for (PersonnelStatus status : values()) {
            mappings.put(status, status.getDescription());
        }
    }

    PersonnelStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
