package zsc.edu.abouerp.api.controller;

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
import zsc.edu.abouerp.entity.domain.Administrator;
import zsc.edu.abouerp.entity.domain.PersonnelStatus;
import zsc.edu.abouerp.entity.domain.Role;
import zsc.edu.abouerp.entity.domain.Title;
import zsc.edu.abouerp.entity.dto.AdministratorDTO;
import zsc.edu.abouerp.entity.vo.AdministratorVO;
import zsc.edu.abouerp.service.exception.ParamErrorException;
import zsc.edu.abouerp.service.exception.TitleNotFoundException;
import zsc.edu.abouerp.service.exception.UserNotFoundException;
import zsc.edu.abouerp.service.exception.UserRepeatException;
import zsc.edu.abouerp.service.mapper.AdministratorMapper;
import zsc.edu.abouerp.service.repository.AdministratorRepository;
import zsc.edu.abouerp.service.security.UserPrincipal;
import zsc.edu.abouerp.service.service.AdministratorService;
import zsc.edu.abouerp.service.service.RoleService;
import zsc.edu.abouerp.service.service.TitleService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final AdministratorRepository administratorRepository;
    private final RoleService roleService;
    private final TitleService titleService;
    private final PasswordEncoder passwordEncoder;
    private final AdministratorService administratorService;

    public AdministratorController(AdministratorRepository administratorRepository,
                                   RoleService roleService,
                                   PasswordEncoder passwordEncoder,
                                   AdministratorService administratorService,
                                   TitleService titleService) {
        this.administratorRepository = administratorRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.administratorService = administratorService;
        this.titleService = titleService;
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
        if (adminVO != null && adminVO.getProbationAccess() != null) {
            administrator.setProbationAccess(adminVO.getProbationAccess());
        }
        if (adminVO != null && adminVO.getMd5() != null) {
            administrator.setMd5(adminVO.getMd5());
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
            Administrator administrator = administratorRepository.getOne(userPrincipal.getId());
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
                wage += role.getBasicSalary();
            }
        }
        administrator.setWage(wage);
        if (administratorVO != null && administratorVO.getTitleId() != null) {
            Title title = titleService.findById(administratorVO.getTitleId()).orElseThrow(TitleNotFoundException::new);
            administrator.setTitle(title);
            administrator.setWage(administrator.getWage()+title.getWage());
        }
        administrator.setRoles(roles);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResultBean<AdministratorDTO> update(
            @PathVariable Integer id,
            @RequestBody AdministratorVO administratorVO) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        if (administratorVO != null && administratorVO.getRole() != null && !administratorVO.getRole().isEmpty()) {
            administrator.setRoles(roleService.findByIdIn(administratorVO.getRole()).stream().collect(Collectors.toSet()));
        }
        if (administratorVO != null && administratorVO.getTitleId() != null) {
            Title title = titleService.findById(administratorVO.getTitleId()).orElseThrow(TitleNotFoundException::new);
            administrator.setTitle(title);
        }
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(update(administrator, administratorVO))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
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
    public ResultBean<EnumMap<PersonnelStatus,String>> getAdminStatus(){
        return ResultBean.ok(PersonnelStatus.mappings);
    }
}
