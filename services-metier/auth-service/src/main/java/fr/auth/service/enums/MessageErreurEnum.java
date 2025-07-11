package fr.auth.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@Getter
@RequiredArgsConstructor
public enum MessageErreurEnum {

    UTILISATEUR_NON_TROUVE("USR_NOT_FOUND", "1001", "Utilisateur non trouvé.", Constants.ERREUR),
    ACCES_NON_AUTORISE("AUTH_001", "2001", "Accès non autorisé pour l'utilisateur courant.", Constants.WARNING),
    URL_INTROUVABLE("URL_404", "2002", "URL demandée introuvable.", Constants.WARNING),
    MAUVAIS_FORMAT_REQUETE("REQ_400", "2003", "Le format de la requête est incorrect.", Constants.WARNING),

    CONFLICT_UTILISATEUR("USER_ALREADY_LINKED", "3001", "Cet utilisateur a déja été enregistrer", Constants.ERREUR),

    // GLOBAL
    ERREUR_TECHNIQUE("MSG-TECH_500", "TECH-001", "Il y a un souci technique. Veuillez réessayer ultérieurement.", Constants.ERREUR),
    BDD_INJOIGNABLE("MSG-DB_503", "TECH-002", "La base de données est injoignable.", Constants.ERREUR),
    ERREUR_ECRITURE_DISQUE("MSG-FICHIER-14", "TECH-009", "Erreur lors de l'écriture sur le disque", Constants.ERREUR),
    ETAT_INVALIDE("MSG-FICHIER-15", "TECH-003", "État non valide pour cette opération.", Constants.ERREUR );

    private final String referenceMetier;

    private final String codeTechnique;

    private final String message;

    private final String type;

    public static class Constants {

        private Constants() {
        }

        public static final String ERREUR = "ERREUR";
        public static final String WARNING = "WARNING";
    }
}
