package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.JwtTokenProvider;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.AuthService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.OrganizationService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.UserAccountService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.RoleRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;
    private final OrganizationService organizationService;
    private final UserAccountService userAccountService;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public OrganizationDto registerOrganization(OrganizationCreateDto createDto) {
        // 1. Crear organización
        OrganizationDto orgDto = organizationService.createOrganization(createDto);
        Organization org = organizationRepository.findById(orgDto.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Error al recuperar organización"));

        // 2. Crear roles básicos
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setDescription("Administrador de la organización");
        adminRole.setOrganization(org);
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("USER");
        userRole.setDescription("Usuario estándar");
        userRole.setOrganization(org);
        roleRepository.save(userRole);

        // 3. Crear usuario administrador inicial
        UserAccountCreateDto adminCreate = new UserAccountCreateDto();
        adminCreate.setUsername(createDto.getAdminUsername());
        adminCreate.setEmail(createDto.getAdminEmail());
        adminCreate.setPassword(createDto.getAdminPassword());
        adminCreate.setFullName(createDto.getAdminFullName());
        UserAccountResponseDto admin = userAccountService.createUser(adminCreate, org.getOrganizationId());

        // 4. Asignar rol ADMIN al usuario
        userAccountService.assignRole(admin.getUserId(), adminRole.getRoleId());

        log.info("Organización y admin creados. Org ID: {}", org.getOrganizationId());
        return orgDto;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        log.info("Intento de login para usuario: {} en orgId: {}", loginRequest.getUsername(),
                loginRequest.getOrganizationId());

        UserAccount user = userAccountRepository
                .findByUsernameAndOrganizationOrganizationId(loginRequest.getUsername(),
                        loginRequest.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        if (!user.getActive()) {
            throw new RuntimeException("Usuario inactivo, contacte al administrador");
        }
        if (!"ACTIVE".equals(user.getOrganization().getStatus())) {
            throw new RuntimeException("Organización suspendida");
        }

        user.setLastLogin(LocalDateTime.now());
        userAccountRepository.save(user);

        String role = user.getUserRoles().isEmpty() ? "USER" : user.getUserRoles().get(0).getRole().getName();
        String token = tokenProvider.generateToken(user.getUserId(), user.getOrganization().getOrganizationId(), role);

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setOrganizationId(user.getOrganization().getOrganizationId());
        response.setOrganizationName(user.getOrganization().getName());
        response.setRoles(Collections.singletonList(role));

        return response;
    }

    @Override
    public void logout(String token) {
        log.info("Cierre de sesión para token");
    }
}