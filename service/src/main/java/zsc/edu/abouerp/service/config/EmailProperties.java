package zsc.edu.abouerp.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Abouerp
 */
@Component
@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {
    private String username;
}
