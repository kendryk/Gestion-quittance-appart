CREATE SCHEMA `Gestion_Acces` DEFAULT CHARACTER SET utf8;

USE Gestion_Acces;

-- Table utilisateur
CREATE TABLE utilisateur (
     id                 BIGINT  PRIMARY KEY AUTO_INCREMENT,
     email              VARCHAR(255)                            NOT NULL UNIQUE COMMENT 'Adresse email unique de l utilisateur, utilisée pour l authentification',
     password           VARCHAR(255)                            NOT NULL COMMENT 'Mot de passe hashé pour l authentification',
     civilite           ENUM ('M', 'MME')                       NULL,
     nom                VARCHAR(100)                            NOT NULL,
     prenom             VARCHAR(100)                            NOT NULL,
     telephone          VARCHAR(20)                             NULL,
     adresse            VARCHAR(255)                            NULL,
     code_postal        VARCHAR(10)                             NULL,
     ville              VARCHAR(100)                            NULL,
     pays               VARCHAR(100)                            NULL,
     statut_compte      ENUM('ACTIF', 'INACTIF', 'SUSPENDU')    NOT NULL,
     role               ENUM('ADMIN', 'UTILISATEUR')            NOT NULL,
     deleted            BOOLEAN                                 NOT NULL DEFAULT FALSE COMMENT 'Indique si l utilisateur est supprimé logiquement',
     date_creation      TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP,
     date_modification  TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Ajout d'un index sur email
CREATE INDEX idx_utilisateur_email ON utilisateur(email);

-- Table document_pdf
CREATE TABLE document_pdf (
      id                    BIGINT          PRIMARY KEY AUTO_INCREMENT,
      utilisateur_id        BIGINT          NOT NULL,
      nom_fichier           VARCHAR(255)    NOT NULL,
      chemin_fichier        VARCHAR(255)    NOT NULL,
      deleted               BOOLEAN         NOT NULL DEFAULT FALSE COMMENT 'Suppression logique du document',
      date_creation         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
      date_modification     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      constraint fk_utilisateur_document foreign key (utilisateur_id) references utilisateur (id)
);

-- Table appartement
CREATE TABLE appartement (
     id                 BIGINT          PRIMARY KEY AUTO_INCREMENT,
     utilisateur_id     BIGINT          NOT NULL,
     adresse            VARCHAR(255)    NOT NULL UNIQUE,
     code_postal        VARCHAR(10)     NOT NULL,
     ville              VARCHAR(100)    NULL,
     nombre_pieces      INTEGER         NOT NULL,
     surface            DECIMAL(10, 2)  NOT NULL CHECK (surface > 0), -- Surface doit être positive
     montant_loyer      DECIMAL(10, 2)  NOT NULL CHECK (montant_loyer > 0), -- Loyer doit être positif
     montant_charge     DECIMAL(10, 2)  NULL CHECK (montant_charge >= 0), -- Charge doit être >= 0
     description        TEXT            NULL COMMENT 'Description facultative du logement',
     deleted            BOOLEAN         NOT NULL DEFAULT FALSE COMMENT 'Suppression logique de l appartement',
     date_creation      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
     date_modification  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     CONSTRAINT fk_utilisateur_appartement FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id)
);

-- Table locataire
CREATE TABLE locataire (
   id                   BIGINT          PRIMARY KEY AUTO_INCREMENT,
   nom                  VARCHAR(100)    NOT NULL,
   prenom               VARCHAR(100)    NOT NULL,
   adresse              VARCHAR(255)    NULL,
   code_postal          VARCHAR(10)     NULL,
   ville                VARCHAR(100)    NULL,
   email                VARCHAR(255)    NOT NULL UNIQUE,
   telephone            VARCHAR(20)     NULL,
   deleted              BOOLEAN         NOT NULL DEFAULT FALSE COMMENT 'Suppression logique du locataire',
   date_creation        TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
   date_modification    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Ajout d'un index pour rechercher rapidement un locataire par email
CREATE INDEX idx_locataire_email ON locataire(email);

-- Table occupation
CREATE TABLE occupation (
    id                 BIGINT           PRIMARY KEY AUTO_INCREMENT,
    locataire_id       BIGINT           NOT NULL,
    appartement_id     BIGINT           NOT NULL,
    date_debut         DATETIME         NOT NULL,
    date_fin           DATETIME         NULL,
    deleted            BOOLEAN          NOT NULL DEFAULT FALSE COMMENT 'Suppression logique de l occupation',
    CONSTRAINT fk_locataire FOREIGN KEY (locataire_id) REFERENCES locataire (id),
    CONSTRAINT fk_appartement FOREIGN KEY (appartement_id) REFERENCES appartement (id),
    CONSTRAINT chk_date_consistency CHECK (date_fin IS NULL OR date_fin > date_debut), -- Vérification cohérence des dates
    CONSTRAINT unq_appartement_date_debut UNIQUE(appartement_id, date_debut) -- Pas de doublon sur même appartement et début
);

-- Ajout d'un index pour accélérer les requêtes impliquant appartement_id
CREATE INDEX idx_occupation_appartement_id ON occupation(appartement_id);

-- Table quittance_loyer
CREATE TABLE quittance_loyer (
    id                  BIGINT                              PRIMARY KEY AUTO_INCREMENT,
    numero_quittance    BIGINT                              NOT NULL UNIQUE,
    occupation_id       BIGINT                              NOT NULL,
    document_pdf_id     BIGINT                              NOT NULL,
    mois_quittance      VARCHAR(7)                          NOT NULL, -- Format : "YYYY-MM"
    date_paiement       DATE                                NOT NULL,
    ville               VARCHAR(100)                        NULL,
    montant_loyer       DECIMAL(10, 2)                      NOT NULL CHECK (montant_loyer > 0),
    montant_charge      DECIMAL(10, 2)                      NOT NULL CHECK (montant_charge >= 0),
    remarque            TEXT                                NULL COMMENT 'Commentaires ou remarques sur la quittance',
    statut_quittance    ENUM('EMISE', 'PAYEE', 'ANNULEE')   NOT NULL COMMENT 'Statut de la quittance',
    deleted             BOOLEAN                             NOT NULL DEFAULT FALSE,
    date_creation       TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP,
    date_modification   TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_occupation FOREIGN KEY (occupation_id) REFERENCES occupation (id),
    CONSTRAINT fk_document_pdf FOREIGN KEY (document_pdf_id) REFERENCES document_pdf (id),
    CONSTRAINT chk_mois_format CHECK (mois_quittance REGEXP '^[0-9]{4}-[0-9]{2}$') -- Validation du format "YYYY-MM"
    );

-- Ajout d'un index pour optimiser les recherches par occupation_id
CREATE INDEX idx_quittance_occupation_id ON quittance_loyer(occupation_id);

-- Table sequence_quittance
CREATE TABLE sequence_quittance (
    id BIGINT                           NOT NULL AUTO_INCREMENT PRIMARY KEY
);
