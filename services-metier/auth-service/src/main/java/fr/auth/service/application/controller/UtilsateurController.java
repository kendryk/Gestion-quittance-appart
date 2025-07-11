package fr.auth.service.application.controller;

import fr.auth.service.application.mapper.UtilisateurDtoMapper;
import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.domain.services.IUtilisateurService;
import fr.auth.service.exception.NotFoundException;
import fr.authservice.application.controller.UtilisateursApi;
import fr.authservice.domain.model.CreeUtilisateur201Response;
import fr.authservice.domain.model.UtilisateurDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class UtilsateurController implements UtilisateursApi {

    private final IUtilisateurService utilisateurService;
    private final UtilisateurDtoMapper utilisateurDtoMapper;

    public UtilsateurController(
            IUtilisateurService utilisateurService,
            UtilisateurDtoMapper utilisateurDtoMapper
    ) {
        this.utilisateurService = utilisateurService;
        this.utilisateurDtoMapper = utilisateurDtoMapper;
    }


    @Override
    public ResponseEntity<CreeUtilisateur201Response> creeUtilisateur(  UtilisateurDto utilisateurDto){
        Utilisateur utilisateur =  utilisateurDtoMapper.applicationToDomain(utilisateurDto);
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

    @Override
    public ResponseEntity<UtilisateurDto> getUtilisateurById(Long utilisateurId) throws NotFoundException {
        Utilisateur utilisateur = utilisateurService.rechercheUtilisateur(utilisateurId);
        return ResponseEntity.ok(utilisateurDtoMapper.domainToApplication(utilisateur));
    }

    @Override
    public ResponseEntity<List<UtilisateurDto>> getUtilisateurs(){
        List<Utilisateur> utilisateurs = utilisateurService.rechercheAllUtilisateurs();
        return ResponseEntity.ok(utilisateurDtoMapper.listDomainToApplication(utilisateurs));
    }
}
