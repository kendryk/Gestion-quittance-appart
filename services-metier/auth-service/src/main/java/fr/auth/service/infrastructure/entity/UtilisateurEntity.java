package fr.auth.service.infrastructure.entity;

import fr.auth.service.enums.UserRole;
import fr.auth.service.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@ToString(of = {"id", "email"})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "utilisateur")
public class UtilisateurEntity extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(name = "civilite")
    private String civilite;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String telephone;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "code_postal", nullable = false, length = 10)
    private String codePostal;

    @Column(name = "ville", nullable = false, length = 100)
    private String ville;

    private String pays;

    @Column(name = "statut_compte")
    @Enumerated(EnumType.STRING)
    private UserStatus statutCompte;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
