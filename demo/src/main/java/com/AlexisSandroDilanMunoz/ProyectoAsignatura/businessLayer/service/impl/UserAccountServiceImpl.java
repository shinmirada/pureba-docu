package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountResponseDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountUpdateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.UserAccountService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserRole;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.RoleRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.UserAccountRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextHelper securityHelper;

    @Override
    public UserAccountResponseDto createUser(UserAccountCreateDto createDto, Long organizationId) {
        log.info("Creando usuario en organización {}: {}", organizationId, createDto.getUsername());

        if (userAccountRepository.existsByUsernameAndOrganizationOrganizationId(createDto.getUsername(),
                organizationId)) {
            throw new IllegalArgumentException("Nombre de usuario ya existe en esta organización");
        }
        if (userAccountRepository.existsByEmailAndOrganizationOrganizationId(createDto.getEmail(), organizationId)) {
            throw new IllegalArgumentException("Correo ya registrado en esta organización");
        }

        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));

        UserAccount user = new UserAccount();
        user.setUsername(createDto.getUsername());
        user.setEmail(createDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(createDto.getPassword()));
        user.setFullName(createDto.getFullName());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setOrganization(org);

        UserAccount saved = userAccountRepository.save(user);

        // 🔽 Asignar rol USER por defecto
        Role defaultRole = roleRepository.findByOrganizationOrganizationIdAndName(organizationId, "USER");
        if (defaultRole != null) {
            assignRole(saved.getUserId(), defaultRole.getRoleId());
        }

        return toResponseDto(saved);
    }

    @Override
    public UserAccountResponseDto updateUser(Long userId, UserAccountUpdateDto updateDto) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        if (!user.getOrganization().getOrganizationId().equals(currentOrg)) {
            throw new RuntimeException("No tiene permisos sobre este usuario");
        }
        if (updateDto.getEmail() != null && !updateDto.getEmail().equals(user.getEmail()) &&
                userAccountRepository.existsByEmailAndOrganizationOrganizationId(updateDto.getEmail(), currentOrg)) {
            throw new IllegalArgumentException("Correo ya en uso");
        }
        if (updateDto.getEmail() != null)
            user.setEmail(updateDto.getEmail());
        if (updateDto.getFullName() != null)
            user.setFullName(updateDto.getFullName());
        return toResponseDto(userAccountRepository.save(user));
    }

    @Override
    public void activateUser(Long userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActive(true);
        userAccountRepository.save(user);
    }

    @Override
    public void deactivateUser(Long userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActive(false);
        userAccountRepository.save(user);
    }

    @Override
    public void assignRole(Long userId, Long roleId) {
        if (userRoleRepository.existsByUserAccountUserIdAndRoleRoleId(userId, roleId)) {
            throw new IllegalArgumentException("El usuario ya tiene ese rol");
        }
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        UserRole ur = new UserRole();
        ur.setUserAccount(user);
        ur.setRole(role);
        userRoleRepository.save(ur);
    }

    @Override
    public void revokeRole(Long userId, Long roleId) {
        userRoleRepository.deleteByUserAccountUserIdAndRoleRoleId(userId, roleId);
    }

    @Override
    public UserAccountResponseDto getUserById(Long userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toResponseDto(user);
    }

    @Override
    public List<UserAccountResponseDto> getUsersByOrganization(Long organizationId) {
        return userAccountRepository.findByOrganizationOrganizationId(organizationId)
                .stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    private UserAccountResponseDto toResponseDto(UserAccount user) {
        UserAccountResponseDto dto = new UserAccountResponseDto();
        dto.setUserId(user.getUserId());
        dto.setOrganizationId(user.getOrganization().getOrganizationId());
        dto.setOrganizationName(user.getOrganization().getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setActive(user.getActive());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());
        // Si necesitas cargar roles, puedes hacerlo aquí
        return dto;
    }
}