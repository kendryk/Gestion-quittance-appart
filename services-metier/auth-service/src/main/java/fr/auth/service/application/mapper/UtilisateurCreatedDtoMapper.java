package fr.auth.service.application.mapper;


import fr.auth.service.domain.model.Utilisateur;
import fr.authservice.domain.model.UtilisateurCreationDto;
import fr.quittance.common.utils.mapper.DateMapperUtil;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DateMapperUtil.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UtilisateurCreatedDtoMapper {

    UtilisateurCreationDto domainToApplication(Utilisateur utilisateur);

    Utilisateur applicationToDomain(UtilisateurCreationDto utilisateurDto);

    List<UtilisateurCreationDto> listDomainToApplication( List<Utilisateur> utilisateur);

}
