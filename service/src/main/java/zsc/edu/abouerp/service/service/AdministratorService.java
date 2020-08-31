package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zsc.edu.abouerp.entity.domain.Administrator;
import zsc.edu.abouerp.entity.domain.PersonnelStatus;
import zsc.edu.abouerp.entity.domain.QAdministrator;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.entity.vo.AdministratorVO;
import zsc.edu.abouerp.service.repository.AdministratorRepository;
import zsc.edu.abouerp.service.repository.RoleRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final RoleRepository roleRepository;

    public AdministratorService(
            AdministratorRepository administratorRepository,
            RoleRepository roleRepository
    ) {
        this.administratorRepository = administratorRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<Administrator> findById(Integer id) {
        return administratorRepository.findById(id);
    }

    @Transactional
    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public long count() {
        return administratorRepository.count();
    }

    public Optional<Administrator> findFirstByUsername(String username) {
        return administratorRepository.findFirstByUsername(username);
    }

    public Page<Administrator> findAll(AdministratorVO administrator, Pageable pageable) {
        QAdministrator qAdministrator = QAdministrator.administrator;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (administrator != null && administrator.getUsername() != null && !administrator.getUsername().isEmpty()) {
            booleanBuilder.and(qAdministrator.username.containsIgnoreCase(administrator.getUsername()));
        }
        if (administrator != null && administrator.getRealName() != null && !administrator.getRealName().isEmpty()) {
            booleanBuilder.and(qAdministrator.realName.containsIgnoreCase(administrator.getRealName()));
        }
        if (administrator != null && administrator.getEmail() != null && !administrator.getEmail().isEmpty()) {
            booleanBuilder.and(qAdministrator.email.containsIgnoreCase(administrator.getEmail()));
        }
        if (administrator != null && administrator.getMobile() != null && !administrator.getMobile().isEmpty()) {
            booleanBuilder.and(qAdministrator.mobile.containsIgnoreCase(administrator.getMobile()));
        }
        if (administrator != null && administrator.getNumber() != null && !administrator.getNumber().isEmpty()) {
            booleanBuilder.and(qAdministrator.number.containsIgnoreCase(administrator.getNumber()));
        }
        if (administrator != null && administrator.getAddress() != null && !administrator.getAddress().isEmpty()) {
            booleanBuilder.and(qAdministrator.address.containsIgnoreCase(administrator.getAddress()));
        }
        if (administrator != null && administrator.getIdCard() != null && !administrator.getIdCard().isEmpty()) {
            booleanBuilder.and(qAdministrator.idCard.containsIgnoreCase(administrator.getIdCard()));
        }
        if (administrator != null && administrator.getWage() != null) {
            booleanBuilder.and(qAdministrator.wage.between(0, administrator.getWage()));
        }
        if (administrator != null && administrator.getSex() != null && !administrator.getSex().isEmpty()) {
            booleanBuilder.and(qAdministrator.sex.containsIgnoreCase(administrator.getSex()));
        }
        if (administrator != null && administrator.getStatus() != null) {
            booleanBuilder.and(qAdministrator.status.eq(administrator.getStatus()));
        }
        if (administrator != null && administrator.getEnabled() != null) {
            booleanBuilder.and(qAdministrator.enabled.eq(administrator.getEnabled()));
        }
        if (administrator != null && administrator.getResign() != null) {
            booleanBuilder.and(qAdministrator.resignMessage.resign.eq(administrator.getResign()));
        }
        return administratorRepository.findAll(booleanBuilder, pageable);
    }

    public Administrator updateRoles(Integer id, List<Integer> roles) {
        Administrator admin = administratorRepository.getOne(id)
                .setRoles(new HashSet<>(roleRepository.findByIdIn(roles)));
        return administratorRepository.save(admin);
    }

    public void delete(Integer id) {
        administratorRepository.deleteById(id);
    }

    public Administrator getOne(Integer id) {
        return administratorRepository.getOne(id);
    }

    public List<Administrator> countByDepartment(Integer departmentId, String rank) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QAdministrator qAdministrator = QAdministrator.administrator;
        booleanBuilder.and(qAdministrator.roles.any().department.id.eq(departmentId));
        booleanBuilder.and(qAdministrator.title.rank.containsIgnoreCase(rank));
        return (List<Administrator>)administratorRepository.findAll(booleanBuilder);
    }

    @Transactional
    public void wageTask(){
        List<Administrator> all = administratorRepository.findAll();
        List<Administrator> after = new ArrayList<>(all.size());
        for (Administrator administrator : all){
            Double wage = 0.0;
            PersonnelStatus status = administrator.getStatus();
            if (status==PersonnelStatus.IN_OFFICE || status==PersonnelStatus.PROBATION){
                List<Role> roles = administrator.getRoles().stream().collect(Collectors.toList());
                for (Role role : roles){
                   wage += role.getBasicSalary();
                }
                wage += administrator.getTitle().getWage();
                Instant offerTime = administrator.getOfferTime();
                wage += (Instant.now().getEpochSecond() - offerTime.getEpochSecond())/31536000*100.0;
                administrator.setWage(wage);
                after.add(administrator);
            }
        }
        administratorRepository.saveAll(after);
    }
}
