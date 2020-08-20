package zsc.edu.abouerp.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

/**
 * 离职申请
 * @author Abouerp
 */
@Entity
@Getter
@Setter
public class ResignMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean resign = false;
    private String reason;
    private Instant time;
}
