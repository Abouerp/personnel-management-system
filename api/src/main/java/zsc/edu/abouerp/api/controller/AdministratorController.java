package zsc.edu.abouerp.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.*;
import zsc.edu.abouerp.entity.dto.AdministratorDTO;
import zsc.edu.abouerp.entity.vo.AdministratorVO;
import zsc.edu.abouerp.service.exception.*;
import zsc.edu.abouerp.service.mapper.AdministratorMapper;
import zsc.edu.abouerp.service.repository.ResignMessageRepository;
import zsc.edu.abouerp.service.security.UserPrincipal;
import zsc.edu.abouerp.service.service.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final RoleService roleService;
    private final TitleService titleService;
    private final PasswordEncoder passwordEncoder;
    private final AdministratorService administratorService;
    private final StorageService storageService;
    private final FileStorageService fileStorageService;
    private final ResignMessageRepository resignMessageRepository;
    private final RoleChangeLoggerService roleChangeLoggerService;
    private final EmailService emailService;

    public AdministratorController(RoleService roleService,
                                   PasswordEncoder passwordEncoder,
                                   AdministratorService administratorService,
                                   TitleService titleService,
                                   StorageService storageService,
                                   FileStorageService fileStorageService,
                                   ResignMessageRepository resignMessageRepository,
                                   RoleChangeLoggerService roleChangeLoggerService,
                                   EmailService emailService) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.administratorService = administratorService;
        this.titleService = titleService;
        this.storageService = storageService;
        this.fileStorageService = fileStorageService;
        this.resignMessageRepository = resignMessageRepository;
        this.roleChangeLoggerService = roleChangeLoggerService;
        this.emailService = emailService;
    }

    private static Administrator update(Administrator administrator, AdministratorVO adminVO) {
        if (adminVO != null && adminVO.getEmail() != null) {
            administrator.setEmail(adminVO.getEmail());
        }
        if (adminVO != null && adminVO.getMobile() != null) {
            administrator.setMobile(adminVO.getMobile());
        }
        if (adminVO != null && adminVO.getEnabled() != null) {
            administrator.setEnabled(adminVO.getEnabled());
        }
        if (adminVO != null && adminVO.getRealName() != null) {
            administrator.setRealName(adminVO.getRealName());
        }
        if (adminVO != null && adminVO.getAddress() != null) {
            administrator.setAddress(adminVO.getAddress());
        }
        if (adminVO != null && adminVO.getNumber() != null) {
            administrator.setNumber(adminVO.getNumber());
        }
        if (adminVO != null && adminVO.getIdCard() != null) {
            administrator.setIdCard(adminVO.getIdCard());
        }
        if (adminVO != null && adminVO.getDescription() != null) {
            administrator.setDescription(adminVO.getDescription());
        }
        if (adminVO != null && adminVO.getSex() != null) {
            administrator.setSex(adminVO.getSex());
        }
        if (adminVO != null && adminVO.getStatus() != null) {
            administrator.setStatus(adminVO.getStatus());
        }
        if (adminVO != null && adminVO.getMd5() != null) {
            administrator.setMd5(adminVO.getMd5());
        }
        if (adminVO != null && adminVO.getOfferTime() != null) {
            administrator.setOfferTime(adminVO.getOfferTime());
        }
        if (adminVO != null && adminVO.getProbationMessage() != null) {
            ProbationMessage probationMessage = new ProbationMessage();
            if (adminVO.getProbationMessage().getProbationAccess() != null) {
                probationMessage.setProbationAccess(adminVO.getProbationMessage().getProbationAccess());
            }
            if (adminVO.getProbationMessage().getProbationStartTime() != null) {
                probationMessage.setProbationStartTime(adminVO.getProbationMessage().getProbationStartTime());
            }
            if (adminVO.getProbationMessage().getProbationEndTime() != null) {
                probationMessage.setProbationEndTime(adminVO.getProbationMessage().getProbationEndTime());
            }
            administrator.setProbationMessage(adminVO.getProbationMessage());
        }
        if (administrator.getResignMessage() != null && adminVO.getResignMessage() != null) {
            ResignMessage resignMessage = administrator.getResignMessage();
            if (adminVO.getResignMessage().getResign() != null) {
                resignMessage.setResign(adminVO.getResignMessage().getResign());
            }
            if (adminVO.getResignMessage().getReason() != null) {
                resignMessage.setReason(adminVO.getResignMessage().getReason());
            }
            if (adminVO.getResignMessage().getTime() != null) {
                resignMessage.setTime(adminVO.getResignMessage().getTime());
            }
            administrator.setResignMessage(resignMessage);
        }
        return administrator;
    }

    /**
     * 防止跨站请求伪造
     */
    @GetMapping("/me")
    public ResultBean<Map<String, Object>> me(@AuthenticationPrincipal Object object, CsrfToken csrfToken) {
        Map<String, Object> map = new HashMap<>(2);
        if (object instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) object;
            Administrator administrator = administratorService.getOne(userPrincipal.getId());
            map.put("user", AdministratorMapper.INSTANCE.toDTO(administrator));
        } else {
            map.put("user", new AdministratorDTO());
        }
        map.put("_csrf", csrfToken);
        return ResultBean.ok(map);
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("principal.id == #id or hasAuthority('USER_UPDATE')")
    public ResultBean<AdministratorDTO> updatePassword(
            @PathVariable Integer id,
//            String srcPassword,
            @RequestParam String password) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
//        if (!passwordEncoder.matches(srcPassword, administrator.getPassword())) {
//            throw new PasswordNotMatchException();
//        }
        administrator.setPassword(passwordEncoder.encode(password));
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PutMapping("/me")
    public ResultBean<AdministratorDTO> updateMyself(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody AdministratorVO adminVO
    ) {
        Administrator admin = administratorService.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
        if (admin.getResignMessage() == null) {
            if (adminVO.getResignMessage() != null) {
                ResignMessage resignMessage = new ResignMessage();
                if (adminVO.getResignMessage().getTime() != null) {
                    resignMessage.setTime(adminVO.getResignMessage().getTime());
                }
                if (adminVO.getResignMessage().getReason() != null) {
                    resignMessage.setReason(adminVO.getResignMessage().getReason());
                }
                if (adminVO.getResignMessage().getResign() != null) {
                    resignMessage.setResign(adminVO.getResignMessage().getResign());
                }
                resignMessage = resignMessageRepository.save(resignMessage);
                admin.setResignMessage(resignMessage);
            }
        }
        if (adminVO.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(adminVO.getPassword()));
        }
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(update(admin, adminVO))));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResultBean<AdministratorDTO> save(@RequestBody @Valid AdministratorVO administratorVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ParamErrorException();
        }
        Administrator administrator = administratorService.findFirstByUsername(administratorVO.getUsername()).orElse(null);
        if (administrator != null) {
            throw new UserRepeatException();
        }
        Set<Role> roles = roleService.findByIdIn(administratorVO.getRole())
                .stream()
                .collect(Collectors.toSet());
        administratorVO.setPassword(passwordEncoder.encode(administratorVO.getPassword()));
        administrator = AdministratorMapper.INSTANCE.toAdmin(administratorVO);
        Double wage = 0.0;
        List<Role> roleList = roles.stream().collect(Collectors.toList());
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                if (role.getBasicSalary() != null) {
                    wage += role.getBasicSalary();
                }
            }
        }
        administrator.setWage(wage);
        if (administratorVO != null && administratorVO.getTitleId() != null) {
            Title title = titleService.findById(administratorVO.getTitleId()).orElseThrow(TitleNotFoundException::new);
            administrator.setTitle(title);
            administrator.setWage(administrator.getWage() + title.getWage());
        }
        administrator.setStatus(PersonnelStatus.PROBATION);
        administrator.setRoles(roles);
        AdministratorDTO administratorDTO = AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator));
        emailService.sendEmail(administratorDTO.getEmail(), administrator.getRealName(), administrator.getStatus(), null);
        RoleChangeLogger roleChangeLogger = new RoleChangeLogger()
                .setRealName(administratorDTO.getRealName())
                .setAdministratorId(administratorDTO.getId())
                .setAfterDepartmentId(roleList.get(0).getDepartment().getId())
                .setAfterDepartmentName(roleList.get(0).getDepartment().getName())
                .setAfterRoleId(roleList.get(0).getId())
                .setAfterRoleName(roleList.get(0).getName())
                .setBeforeRoleId(roleList.get(0).getId())
                .setBeforeRoleName(roleList.get(0).getName())
                .setBeforeDepartmentId(roleList.get(0).getDepartment().getId())
                .setBeforeDepartmentName(roleList.get(0).getDepartment().getName());
        roleChangeLoggerService.save(roleChangeLogger);
        return ResultBean.ok(administratorDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResultBean<AdministratorDTO> update(
            @PathVariable Integer id,
            @RequestBody AdministratorVO administratorVO) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        List<Role> roles = administrator.getRoles().stream().collect(Collectors.toList());
        if (administratorVO != null && administratorVO.getRole() != null && !administratorVO.getRole().isEmpty()) {
            List<Role> newRole = roleService.findByIdIn(administratorVO.getRole());
            RoleChangeLogger roleChangeLogger = new RoleChangeLogger()
                    .setAdministratorId(administrator.getId())
                    .setBeforeRoleId(roles.get(0).getId())
                    .setBeforeRoleName(roles.get(0).getName())
                    .setAfterRoleId(newRole.get(0).getId())
                    .setAfterRoleName(newRole.get(0).getName())
                    .setBeforeDepartmentId(roles.get(0).getDepartment().getId())
                    .setBeforeDepartmentName(roles.get(0).getDepartment().getName())
                    .setAfterDepartmentId(newRole.get(0).getDepartment().getId())
                    .setAfterDepartmentName(newRole.get(0).getDepartment().getName())
                    .setRealName(administrator.getRealName());
            roleChangeLoggerService.save(roleChangeLogger);
            administrator.setRoles(newRole.stream().collect(Collectors.toSet()));
            emailService.sendEmail(administrator.getEmail(), administrator.getRealName(), null, newRole.get(0).getName());
        }
        if (administratorVO != null && administratorVO.getTitleId() != null) {
            Title title = titleService.findById(administratorVO.getTitleId()).orElseThrow(TitleNotFoundException::new);
            administrator.setTitle(title);
        }
        if (administratorVO != null && administratorVO.getStatus() != null) {
            if (administratorVO.getStatus().equals(PersonnelStatus.IN_OFFICE) && !administrator.getStatus().equals(PersonnelStatus.IN_OFFICE)) {
                emailService.sendEmail(administrator.getEmail(), administrator.getRealName(), administrator.getStatus(), null);
            }
            if (administratorVO.getStatus().equals(PersonnelStatus.UN_OFFICE)) {
                //离职记录
                RoleChangeLogger roleChangeLogger = new RoleChangeLogger()
                        .setAdministratorId(administrator.getId())
                        .setBeforeDepartmentId(roles.get(0).getDepartment().getId())
                        .setBeforeDepartmentName(roles.get(0).getDepartment().getName())
                        .setBeforeRoleId(roles.get(0).getId())
                        .setBeforeRoleName(roles.get(0).getName())
                        .setAfterDepartmentId(roles.get(0).getDepartment().getId())
                        .setAfterDepartmentName(roles.get(0).getDepartment().getName())
                        .setAfterRoleId(roles.get(0).getId())
                        .setAfterRoleName(roles.get(0).getName())
                        .setResign(true);
                roleChangeLoggerService.save(roleChangeLogger);
                emailService.sendEmail(administrator.getEmail(), administrator.getRealName(), administrator.getStatus(), null);
            }
        }
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(update(administrator, administratorVO))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        if (administrator.getMd5() != null) {
            log.info("delete personnel has md5========================= {}" ,administrator.getMd5());
            Storage storage = storageService.findByMD5(administrator.getMd5());
            if (storage.getCount() - 1 == 0) {
                fileStorageService.delete(storage.getMd5());
                storageService.deleteById(storage.getId());
            } else {
                storage.setCount(storage.getCount() - 1);
                storageService.save(storage);
            }
        }
        administratorService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResultBean<Page<AdministratorDTO>> findAll(
            @PageableDefault Pageable pageable,
            AdministratorVO administrator) {
        return ResultBean.ok(administratorService.findAll(administrator, pageable).map(AdministratorMapper.INSTANCE::toDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResultBean<AdministratorDTO> findById(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administrator));
    }


    @GetMapping("/judge")
    public ResultBean<Boolean> judgeUserName(String username) {
        Administrator administrator = administratorService.findFirstByUsername(username).orElse(null);
        if (administrator != null) {
            return ResultBean.ok(true);
        } else {
            return ResultBean.ok(false);
        }
    }

    @GetMapping("/personnel-status")
    public ResultBean<EnumMap<PersonnelStatus, String>> getAdminStatus() {
        return ResultBean.ok(PersonnelStatus.mappings);
    }

}
