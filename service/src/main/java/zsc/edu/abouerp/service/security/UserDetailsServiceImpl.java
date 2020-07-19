package zsc.edu.abouerp.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.entity.domain.Administrator;
import zsc.edu.abouerp.service.exception.UnauthorizedException;
import zsc.edu.abouerp.service.mapper.AdministratorMapper;
import zsc.edu.abouerp.service.repository.AdministratorRepository;

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
