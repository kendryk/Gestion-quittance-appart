package fr.auth.service.application.controller;

import fr.auth.service.application.mapper.UtilisateurCreatedDtoMapper;
import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.domain.services.IUtilisateurService;
import fr.authservice.application.controller.RegisterApi;
import fr.authservice.domain.model.CreeUtilisateur201Response;
import fr.authservice.domain.model.UtilisateurCreationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegisterController implements RegisterApi {

    final UtilisateurCreatedDtoMapper utilisateurCreatedDtoMapper;
    final IUtilisateurService utilisateurService;

    public RegisterController(
            UtilisateurCreatedDtoMapper utilisateurCreatedDtoMapper,
            IUtilisateurService utilisateurService
    ) {
        this.utilisateurCreatedDtoMapper = utilisateurCreatedDtoMapper;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public ResponseEntity<CreeUtilisateur201Response> creeUtilisateur(  UtilisateurCreationDto utilisateurCreationDto){
        Utilisateur utilisateur =  utilisateurCreatedDtoMapper.applicationToDomain(utilisateurCreationDto);
        Long id =  utilisateurService.creer(utilisateur);
        log.info("Création de l'utilisateur {}", id);

        // Construire une réponse de succès
        CreeUtilisateur201Response response = new CreeUtilisateur201Response()
                .id(Math.toIntExact(id)) // Conversion Long -> Integer (attention à la taille)
                .message("Utilisateur créé avec succès.");

        // Log pour audit/debug
        log.info("Création de l'utilisateur avec ID: {}", id);

        // Retourner une réponse HTTP 201
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
