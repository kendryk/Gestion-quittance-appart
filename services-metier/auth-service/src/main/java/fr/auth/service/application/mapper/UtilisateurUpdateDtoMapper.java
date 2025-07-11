package fr.auth.service.application.mapper;

import fr.auth.service.domain.model.Utilisateur;
import fr.authservice.domain.model.UtilisateurUpdateDto;
import fr.quittance.common.utils.mapper.JsonNullableMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {JsonNullableMapper.class, CiviliteEnumMapper.class}
)
public interface UtilisateurUpdateDtoMapper {

    // Mapping : Entité -> DTO
    @Mapping(target = "email", source = "email")
    @Mapping(target = "civilite", source = "civilite", qualifiedByName = "civiliteEnumToJsonNullableDtoEnum")
    @Mapping(target = "telephone", source = "telephone", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "adresse", source = "adresse", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "codePostal", source = "codePostal", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "ville", source = "ville", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "pays", source = "pays", qualifiedByName = "stringToJsonNullable")
    UtilisateurUpdateDto domainToApplication(Utilisateur utilisateur);

    // Mapping : DTO -> Entité
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "statutCompte", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "civilite", source = "civilite", qualifiedByName = "jsonNullableDtoEnumToCiviliteEnum")
    @Mapping(target = "telephone", source = "telephone", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "adresse", source = "adresse", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "codePostal", source = "codePostal", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "ville", source = "ville", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "pays", source = "pays", qualifiedByName = "jsonNullableToString")
    Utilisateur applicationToDomain(UtilisateurUpdateDto utilisateurDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "statutCompte", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "civilite", source = "civilite", qualifiedByName = "jsonNullableDtoEnumToCiviliteEnum")
    @Mapping(target = "telephone", source = "telephone", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "adresse", source = "adresse", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "codePostal", source = "codePostal", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "ville", source = "ville", qualifiedByName = "jsonNullableToString")
    @Mapping(target = "pays", source = "pays", qualifiedByName = "jsonNullableToString")
    void updateFromDto(@MappingTarget Utilisateur utilisateur, UtilisateurUpdateDto utilisateurUpdateDto);

    // Mapping pour une liste d'entités
    List<UtilisateurUpdateDto> listDomainToApplication(List<Utilisateur> utilisateurs);
}
