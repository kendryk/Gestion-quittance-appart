package fr.auth.service.domain.services;



import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.exception.NotFoundException;

import java.util.List;

public interface IUtilisateurService {

    /**
     * Crée un nouvel utilisateur.
     *
     * @param utilisateur les informations de l'utilisateur à créer.
     * @return l'ID du nouvel utilisateur généré automatiquement.
     */
    Long creer(Utilisateur utilisateur);


    /**
     * Recherche un utilisateur par son ID.
     *
     * @param utilisateurId l'identifiant unique de l'utilisateur.
     * @return l'utilisateur correspondant à l'ID fourni.
     * @throws NotFoundException si l'utilisateur avec cet ID n'existe pas.
     */
    Utilisateur rechercheUtilisateur(long utilisateurId) throws NotFoundException;

    /**
     * Recherche tous les utilisateurs.
     *
     * @return une liste de tous les utilisateurs enregistrés.
     */
    List<Utilisateur> rechercheAllUtilisateurs();

    /**
     * Supprime un utilisateur par identifiant.
     *
     * @param identifiantUtilisateur l'identifiant unique de l'utilisateur à supprimer.
     * @return un message confirmant la suppression de l'utilisateur.
     */
    String deleteUtilisateur(String identifiantUtilisateur) throws NotFoundException;

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param utilisateur les informations de l'utilisateur à mettre à jour.
     * @return l'utilisateur mis à jour.
     * @throws NotFoundException si l'utilisateur à mettre à jour n'existe pas.
     */
    Utilisateur updateUtilisateur ( Utilisateur utilisateur) throws NotFoundException;
}
