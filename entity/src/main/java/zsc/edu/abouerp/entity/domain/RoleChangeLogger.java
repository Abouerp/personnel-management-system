package zsc.edu.abouerp.entity.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Getter
@Setter
@Entity
@ToString
@Accessors(chain = true)
public class RoleChangeLogger implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer administratorId;
    private String realName;
    private Integer beforeDepartmentId;
    private String beforeDepartmentName;
    private Integer afterDepartmentId;
    private String afterDepartmentName;

    private Integer beforeRoleId;
    private String beforeRoleName;
    private Integer afterRoleId;
    private String afterRoleName;
    @CreatedBy
    private Integer createBy;
    @LastModifiedBy
    private Integer updateBy;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
