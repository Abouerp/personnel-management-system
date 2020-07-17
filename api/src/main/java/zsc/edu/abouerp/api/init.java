package zsc.edu.abouerp.api;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import zsc.edu.abouerp.api.domain.Administrator;
import zsc.edu.abouerp.api.domain.Authority;
import zsc.edu.abouerp.api.domain.Role;
import zsc.edu.abouerp.api.exception.RoleNotFoundException;
import zsc.edu.abouerp.api.repository.AdministratorRepository;
import zsc.edu.abouerp.api.repository.RoleRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
                    .setEnabled(true)
                    .setRoles(Stream.of(role).collect(Collectors.toSet()));
            administratorRepository.save(administrator);
        }
    }
}
