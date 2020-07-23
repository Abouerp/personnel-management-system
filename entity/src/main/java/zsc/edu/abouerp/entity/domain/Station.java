package zsc.edu.abouerp.entity.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 岗位
 * @author Abouerp
 */
@Entity
@Data
public class Station implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String number;

    private String description;

    @ManyToOne
    private Department department;
}
