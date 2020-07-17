package zsc.edu.abouerp.api.config;


import org.springframework.data.domain.AuditorAware;
import zsc.edu.abouerp.api.utils.UserUtils;

import java.util.Optional;

/**
 * @author Abouerp
 */
public class UserAuditorConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        return UserUtils.getCurrentAuditor();
    }
}
