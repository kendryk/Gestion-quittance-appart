package fr.auth.service.application.controller;

import fr.auth.service.application.mapper.UtilisateurDtoMapper;
import fr.auth.service.application.mapper.UtilisateurUpdateDtoMapper;
import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.domain.services.IUtilisateurService;
import fr.auth.service.exception.NotFoundException;
import fr.authservice.application.controller.UtilisateursApi;
import fr.authservice.domain.model.UtilisateurDto;
import fr.authservice.domain.model.UtilisateurUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UtilisateurController implements UtilisateursApi {

    private final IUtilisateurService utilisateurService;
    private final UtilisateurDtoMapper utilisateurDtoMapper;
    private final UtilisateurUpdateDtoMapper utilisateurUpdateDtoMapper;

    public UtilisateurController(
            IUtilisateurService utilisateurService,
            UtilisateurDtoMapper utilisateurDtoMapper,
            UtilisateurUpdateDtoMapper utilisateurUpdateDtoMapper
    ) {
        this.utilisateurService = utilisateurService;
        this.utilisateurDtoMapper = utilisateurDtoMapper;
        this.utilisateurUpdateDtoMapper = utilisateurUpdateDtoMapper;
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

    @Override
    public ResponseEntity<Boolean> softDeleteUtilisateur(String identifiantUtilisateur) throws NotFoundException {
        String response =  utilisateurService.deleteUtilisateur(identifiantUtilisateur);
        return ResponseEntity.ok(response.isEmpty() ? Boolean.FALSE : Boolean.TRUE );
    }

    @Override
    public ResponseEntity<Boolean> updateUtilisateurById( Long utilisateurId, UtilisateurUpdateDto utilisateurUpdateDto) throws NotFoundException {
        log.info("Mise à jour de l'Utilisateur ID : {}", utilisateurId);
        // Vérifie si l'utilisateur existe et renvoie une erreur si non trouvé
        Utilisateur oldUtilisateur =  utilisateurService.rechercheUtilisateur(utilisateurId);

        // Mise à jour de l'entité à partir du DTO
        Utilisateur utilisateur = utilisateurUpdateDtoMapper.applicationToDomain(utilisateurUpdateDto);
        utilisateur.setId(utilisateurId);
        utilisateur.setRole(oldUtilisateur.getRole());
        utilisateur.setStatutCompte(oldUtilisateur.getStatutCompte());

        // Sauvegarde des modifications
        utilisateurService.updateUtilisateur(utilisateur);

        // Convertit l'entité mise à jour en DTO pour la réponse
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
