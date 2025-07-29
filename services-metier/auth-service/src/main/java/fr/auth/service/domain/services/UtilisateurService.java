package fr.auth.service.domain.services;


import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.domain.repository.IUtilisateurRepository;
import fr.auth.service.enums.MessageErreurEnum;
import fr.auth.service.enums.UserRole;
import fr.auth.service.enums.UserStatus;
import fr.auth.service.exception.AppartementDejaLieException;
import fr.auth.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Slf4j
public class UtilisateurService implements IUtilisateurService {

    private final IUtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(
            IUtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Long creer(Utilisateur utilisateur) {

        try {
            // Définir les valeurs par défaut
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            utilisateur.setStatutCompte(UserStatus.ACTIF.toString()); // ou INACTIF selon votre logique
            utilisateur.setRole(UserRole.UTILISATEUR.toString()); // rôle par défaut
            utilisateur.setDeleted(false);

            return utilisateurRepository.creer(utilisateur);
        } catch (DataIntegrityViolationException e) {
            log.error(MessageErreurEnum.CONFLICT_UTILISATEUR.getMessage() + e);
            throw new AppartementDejaLieException(MessageErreurEnum.CONFLICT_UTILISATEUR.getMessage());
        }
    }

    @Override
    public Utilisateur rechercheUtilisateur(long utilisateurId) throws NotFoundException {
        return  utilisateurRepository.rechercheUtilisateur(utilisateurId);
    }

    @Override
    public List<Utilisateur> rechercheAllUtilisateurs() {
        return utilisateurRepository.rechercheAllUtilisateurs();
    }

    @Override
    public String deleteUtilisateur(String identifiantUtilisateur) throws NotFoundException {
        Utilisateur utilisateur =  utilisateurRepository.rechercheUtilisateurByEmail(identifiantUtilisateur);
        // On modifie le statut de l'utilisateur
        utilisateur.setStatutCompte(UserStatus.INACTIF.name());
        utilisateurRepository.update(utilisateur);
        log.info("Modification réussie de l'utilisateur : {}", utilisateur);
        return String.format("Succès de la modification de l'utilisateur : %s", utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur (Utilisateur utilisateur) throws NotFoundException {
        // Recherche de l'utilisateur en base à partir de son ID
        Utilisateur existingUtilisateur = utilisateurRepository.rechercheUtilisateur(utilisateur.getId());
        // Mise à jour de l'entité existante  en tenant compte des valeurs optionnelles
        Optional.ofNullable(utilisateur.getNom()).ifPresent(existingUtilisateur ::setNom);
        Optional.ofNullable(utilisateur.getPrenom()).ifPresent(existingUtilisateur ::setPrenom);
        Optional.ofNullable(utilisateur.getEmail()).ifPresent(existingUtilisateur ::setEmail);
        Optional.ofNullable(utilisateur.getTelephone()).ifPresent(existingUtilisateur ::setTelephone);
        Optional.ofNullable(utilisateur.getAdresse()).ifPresent(existingUtilisateur ::setAdresse);
        Optional.ofNullable(utilisateur.getCodePostal()).ifPresent(existingUtilisateur ::setCodePostal);
        Optional.ofNullable(utilisateur.getVille()).ifPresent(existingUtilisateur ::setVille);
        Optional.ofNullable(utilisateur.getPays()).ifPresent(existingUtilisateur ::setPays);

        // Mise à jour de l'entité à partir du DTO
        existingUtilisateur.setStatutCompte(utilisateur.getStatutCompte());
        existingUtilisateur.setRole(utilisateur.getRole());

        // Sauvegarde des modifications dans la base de données
        try {
            utilisateurRepository.update(existingUtilisateur);
            return existingUtilisateur;

        } catch (DataIntegrityViolationException e) {
            log.error(MessageErreurEnum.CONFLICT_UTILISATEUR.getMessage() + e);
            throw new AppartementDejaLieException(MessageErreurEnum.CONFLICT_UTILISATEUR.getMessage());
        }
    }


}
