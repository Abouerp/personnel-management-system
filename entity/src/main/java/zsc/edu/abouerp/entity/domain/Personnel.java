package zsc.edu.abouerp.entity.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Abouerp
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Personnel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String mobile;
    private String email;
    private String address;
    private String number;
    private String idCard;
    private String description;
    private Double wage;
    private String sex;
    private PersonnelStatus status;

}
