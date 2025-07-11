package fr.auth.service.infrastructure.mapper;

import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.infrastructure.entity.UtilisateurEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UtilisateurMapper {

    UtilisateurEntity domainToEntity(Utilisateur utilisateurDomain);

    Utilisateur entityToDomain(UtilisateurEntity utilisateurEntity);

    List<Utilisateur> listEntityToDomain(List<UtilisateurEntity> listUtilisateur);
}
