package fr.auth.service.infrastructure.adapter.persistence;

import fr.auth.service.infrastructure.entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUtilisateurJpaRepository extends JpaRepository<UtilisateurEntity, Long> {

    Optional<UtilisateurEntity> findByEmail(String email);
}
