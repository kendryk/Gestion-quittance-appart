package fr.auth.service.domain.model;

import fr.quittance.common.enums.CiviliteEnum;
import lombok.*;

import java.sql.Timestamp;

@EqualsAndHashCode(of = {"id", "email", "nom", "prenom", "telephone", "adresse", "codePostal", "ville", "pays"})
@ToString(of = {"id", "email"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Utilisateur {
    private Long id;
    private String email;
    private CiviliteEnum civilite;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String codePostal;
    private String ville;
    private String pays;
    private String statutCompte;
    private String role;
    private Timestamp dateCreation;
    private Timestamp dateModification;
}
