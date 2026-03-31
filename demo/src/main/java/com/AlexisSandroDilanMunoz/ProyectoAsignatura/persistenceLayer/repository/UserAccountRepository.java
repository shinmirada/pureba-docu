package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    // Buscar usuario por username (login — RF02)
    Optional<UserAccount> findByUsername(String username);

    // Buscar usuario por email (login alternativo — RF02)
    Optional<UserAccount> findByEmail(String email);

    // Buscar usuario por username + organización (multi-tenant correcto — RF02)
    Optional<UserAccount> findByUsernameAndOrganizationOrganizationId(String username, Long organizationId);

    // Buscar usuario por email + organización (RF02)
    Optional<UserAccount> findByEmailAndOrganizationOrganizationId(String email, Long organizationId);

    // Listar usuarios de una organización (RF16)
    List<UserAccount> findByOrganizationOrganizationId(Long organizationId);

    // Filtrar usuarios activos/inactivos dentro de la organización (RF14)
    List<UserAccount> findByOrganizationOrganizationIdAndActive(Long organizationId, Boolean active);

    // Validar si ya existe username en la organización (RF12)
    boolean existsByUsernameAndOrganizationOrganizationId(String username, Long organizationId);

    // Validar si ya existe email en la organización (RF12)
    boolean existsByEmailAndOrganizationOrganizationId(String email, Long organizationId);
}
