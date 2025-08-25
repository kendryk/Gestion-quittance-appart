package fr.auth.service.domain.repository;


import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUtilisateurRepository {


    /**
     * Création d'un utilisateur en base de données
     *
     * @param utilisateur un objet {@link Utilisateur}
     * @return l'id du nouvel utilisateur
     */
    Long creer(Utilisateur utilisateur);

    /**
     * Recherche d'un utilisateur.
     *
     * @param utilisateurId
     * @return l'utilisateur en rapport a l'id
     */
    Utilisateur rechercheUtilisateur(long utilisateurId) throws NotFoundException;

    /**
     * Recherche de tous les utilisateurs.
     *
     * @return liste d'utilisateurs
     */
     List<Utilisateur> rechercheAllUtilisateurs();

    /**
     * recherche Utilisateur  par son Email
     * @param identifiantUtilisateur un String
     * @return utilisateur
     */
    Utilisateur rechercheUtilisateurByEmail(String identifiantUtilisateur) throws NotFoundException;

    /**
     * Modification d'un utilisateur en base de données
     *
     * @param utilisateur un objet {@link Utilisateur}
     * @return le mail de l'utilisateur
     */
    Utilisateur update(Utilisateur utilisateur) throws NotFoundException;
}
