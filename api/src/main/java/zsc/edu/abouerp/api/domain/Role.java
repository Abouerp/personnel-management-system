package zsc.edu.abouerp.api.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
 * 角色信息表
 *
 * @author Abouerp
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities = new HashSet<>();

    @CreatedBy
    private Integer createBy;

    @LastModifiedBy
    private Integer updateBy;

    @CreationTimestamp
    private Instant createTime;

    @UpdateTimestamp
    private Instant updateTime;

}
