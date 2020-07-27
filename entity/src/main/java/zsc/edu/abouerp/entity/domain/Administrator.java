package zsc.edu.abouerp.entity.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Abouerp
 */

@Entity
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Administrator implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String realName;
    private String mobile;
    private String email;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;

    private String address;
    private String number;
    private String idCard;
    private String description;
    private Double wage;
    private String sex;
    /**
     * 员工的状态
     */
    private PersonnelStatus status;
    /**
     * 试用期评价
     */
    private String probationAccess;
    private Instant officeTime;
    private Title title;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
    @CreatedBy
    private Integer createBy;
    @LastModifiedBy
    private Integer updateBy;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
