package zsc.edu.abouerp.entity.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
@Embeddable
public class ProbationMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 试用期评价
     */
    private String probationAccess;
    private Instant probationStartTime;
    private Instant probationEndTime;
}
