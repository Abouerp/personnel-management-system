package zsc.edu.abouerp.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zsc.edu.abouerp.entity.domain.Administrator;
import zsc.edu.abouerp.entity.domain.Authority;
import zsc.edu.abouerp.entity.domain.PersonnelStatus;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.service.repository.AdministratorRepository;
import zsc.edu.abouerp.service.repository.RoleRepository;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abouerp
 */
@Component
public class init implements CommandLineRunner {

    private final AdministratorRepository administratorRepository;
    private final RoleRepository roleRepository;

    public init(AdministratorRepository administratorRepository,
                RoleRepository roleRepository) {
        this.administratorRepository = administratorRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = administratorRepository.count();
        if (count == 0) {
            Role role = new Role()
                    .setName("超级管理员")
                    .setAuthorities(Arrays.stream(Authority.values()).collect(Collectors.toSet()));
            role = roleRepository.save(role);
            Administrator administrator = new Administrator()
                    .setUsername("admin")
                    .setPassword("{noop}admin")
                    .setAccountNonExpired(true)
                    .setAccountNonLocked(true)
                    .setCredentialsNonExpired(true)
                    .setStatus(PersonnelStatus.IN_OFFICE)
                    .setEnabled(true)
                    .setNumber("00001")
                    .setRealName("杰哥哥")
                    .setAddress("电子科技大学中山学院")
                    .setEmail("12345679@qqq.com")
                    .setMobile("1888999999")
                    .setRoles(Stream.of(role).collect(Collectors.toSet()));
            administratorRepository.save(administrator);
        }
    }
}
