package fr.auth.service.infrastructure.adapter;


import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.domain.repository.IUtilisateurRepository;
import fr.auth.service.enums.MessageErreurEnum;
import fr.auth.service.exception.NotFoundException;
import fr.auth.service.infrastructure.adapter.persistence.IUtilisateurJpaRepository;
import fr.auth.service.infrastructure.entity.UtilisateurEntity;
import fr.auth.service.infrastructure.mapper.UtilisateurMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UtilisateurRepository implements IUtilisateurRepository {

    private final IUtilisateurJpaRepository utilisateurJpaRepository;

    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurRepository(IUtilisateurJpaRepository utilisateurJpaRepository,
            UtilisateurMapper utilisateurMapper) {
        this.utilisateurJpaRepository = utilisateurJpaRepository;
        this.utilisateurMapper = utilisateurMapper;
    }

    @Override
    public Long creer(Utilisateur utilisateur) {
        //   generer le mapper utilisateur
        UtilisateurEntity utilisateurEntity = utilisateurMapper.domainToEntity(utilisateur);
        log.info("sauvegarde de l'utilisateur dans le dépôt");
        return utilisateurJpaRepository.save(utilisateurEntity).getId();
    }

    @Override
    public Utilisateur rechercheUtilisateur(long utilisateurId) throws NotFoundException {
        log.info("Recherche de l'utilisateur avec ID : " + utilisateurId);
        return utilisateurJpaRepository.findById(utilisateurId)
                .map(utilisateurMapper::entityToDomain)
                .orElseThrow( () -> {
                    log.error(MessageErreurEnum.UTILISATEUR_NON_TROUVE.getMessage(), "ID :" + utilisateurId);
                    return new NotFoundException(
                            MessageErreurEnum.UTILISATEUR_NON_TROUVE.getMessage(),
                            MessageErreurEnum.UTILISATEUR_NON_TROUVE.getCodeTechnique());
                });
    }

    @Override
    public List<Utilisateur> rechercheAllUtilisateurs() {
        List<UtilisateurEntity> listUtilisateurEntity = utilisateurJpaRepository.findAll();
        return utilisateurMapper.listEntityToDomain(listUtilisateurEntity);
    }

    @Override
    public Utilisateur rechercheUtilisateurByEmail(String identifiantUtilisateur) throws NotFoundException {
        return utilisateurJpaRepository.findByEmail(identifiantUtilisateur)
                .filter(utilisateur -> !utilisateur.getEmail().isEmpty())
                .map(utilisateurMapper::entityToDomain)
                .orElseThrow( () -> {
                    log.error(MessageErreurEnum.UTILISATEUR_NON_TROUVE.getMessage(), "identifiant :" + identifiantUtilisateur);
                    return new NotFoundException(
                            MessageErreurEnum.UTILISATEUR_NON_TROUVE.getMessage(),
                            MessageErreurEnum.UTILISATEUR_NON_TROUVE.getCodeTechnique());
                });
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) throws NotFoundException {
        // Récupère l'entité existante (managée par JPA)
        UtilisateurEntity existingEntity = utilisateurJpaRepository.findById(utilisateur.getId())
                .orElseThrow( () -> new NotFoundException("Utilisateur non trouvé"));

                // Met à jour uniquement les champs nécessaires
        utilisateurMapper.updateEntityFromDomain(utilisateur, existingEntity);

        log.info("Sauvegarde de l'utilisateur dans le dépôt");
        utilisateurJpaRepository.save(existingEntity);
        return utilisateurMapper.entityToDomain(existingEntity);
    }
}
