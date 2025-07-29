package fr.auth.service.infrastructure.mapper;

import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.enums.UserRole;
import fr.auth.service.enums.UserStatus;
import fr.auth.service.infrastructure.entity.UtilisateurEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {UserStatus.class, UserRole.class})
public interface UtilisateurMapper {

    @Mapping(target = "deleted", defaultValue = "false")
    @Mapping(target = "statutCompte", expression = "java(UserStatus.fromValue(utilisateurDomain.getStatutCompte()))")
    @Mapping(target = "role", expression = "java(UserRole.fromValue(utilisateurDomain.getRole()))")
    UtilisateurEntity domainToEntity(Utilisateur utilisateurDomain);

    Utilisateur entityToDomain(UtilisateurEntity utilisateurEntity);

    List<Utilisateur> listEntityToDomain(List<UtilisateurEntity> listUtilisateur);

}
