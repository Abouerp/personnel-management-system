package zsc.edu.abouerp.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.api.domain.Administrator;
import zsc.edu.abouerp.api.exception.UnauthorizedException;
import zsc.edu.abouerp.api.mapper.AdministratorMapper;
import zsc.edu.abouerp.api.repository.AdministratorRepository;

/**
 * @author Abouerp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    public UserDetailsServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Administrator administrator = administratorRepository.findFirstByUsername(s).orElseThrow(null);
        if (administrator == null) {
            throw new UnauthorizedException();
        }
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
