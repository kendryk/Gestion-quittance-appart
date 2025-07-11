package fr.auth.service.application.mapper;


import fr.auth.service.domain.model.Utilisateur;
import fr.authservice.domain.model.UtilisateurDto;
import fr.quittance.common.utils.mapper.DateMapperUtil;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DateMapperUtil.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UtilisateurDtoMapper {

    @Mapping(source = "dateCreation", target = "dateCreation", qualifiedByName = "timestampToLocalDate")
    @Mapping(source = "dateModification", target = "dateModification", qualifiedByName = "timestampToLocalDate")
    @Mapping(target = "fullName", expression = "java(buildFullName(utilisateur.getPrenom(), utilisateur.getNom()))")
    UtilisateurDto domainToApplication(Utilisateur utilisateur);

    @Mapping(source = "dateCreation", target = "dateCreation", qualifiedByName = "localDateToTimestamp")
    @Mapping(source = "dateModification", target = "dateModification", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    Utilisateur applicationToDomain(UtilisateurDto utilisateurDto);

    List<UtilisateurDto> listDomainToApplication( List<Utilisateur> utilisateur);

    // Helper method to build full name from prenom and nom
    default String buildFullName(String prenom, String nom) {
        if (prenom == null && nom == null) {
            return null;
        }
        if (prenom == null) {
            return nom;
        }
        if (nom == null) {
            return prenom;
        }
        return prenom + " " + nom;
    }
}
