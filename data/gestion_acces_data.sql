USE Gestion_Acces;

-- Insertion des utilisateurs
INSERT INTO utilisateur (email, password, civilite, nom, prenom, telephone, adresse, code_postal, ville, pays, statut_compte, role, deleted)
VALUES
    -- Mot de passe "test" (à hasher pour un environnement réel)
    ('john.doe@example.com', 'hashed_password_1', 'M', 'Doe', 'John', '0123456789', '123 Rue de Paris', '75001', 'Paris', 'France', 'ACTIF', 'ADMIN', FALSE),
    ('jane.smith@example.com', 'hashed_password_2', 'MME', 'Smith', 'Jane', '0987654321', '456 Avenue de Lyon', '69001', 'Lyon', 'France', 'ACTIF', 'UTILISATEUR', FALSE);

-- Insertion des documents PDF associés aux utilisateurs
INSERT INTO document_pdf (utilisateur_id, nom_fichier, chemin_fichier, deleted)
VALUES
    (1, 'document1.pdf', '/path/to/document1.pdf', FALSE),
    (2, 'document2.pdf', '/path/to/document2.pdf', FALSE);

-- Insertion des appartements
INSERT INTO appartement (utilisateur_id, adresse, code_postal, ville, nombre_pieces, surface, montant_loyer, montant_charge, description, deleted)
VALUES
    (1, '789 Boulevard de Nice', '06000', 'Nice', 3, 75.5, 1200.00, 100.00, 'Appartement avec grande terrasse', FALSE),
    (2, '101 Rue de Marseille', '13001', 'Marseille', 2, 50.0, 800.00, 80.00, 'Appartement rénové récemment', FALSE);

-- Insertion des locataires
INSERT INTO locataire (nom, prenom, adresse, code_postal, ville, email, telephone, deleted)
VALUES
    ('Martin', 'Paul', '789 Boulevard de Nice', '06000', 'Nice', 'paul.martin@example.com', '0112233445', FALSE),
    ('Dupont', 'Marie', '101 Rue de Marseille', '13001', 'Marseille', 'marie.dupont@example.com', '0223344556', FALSE);

-- Insertion des occupations (vérification des dates cohérentes)
INSERT INTO occupation (locataire_id, appartement_id, date_debut, date_fin, deleted)
VALUES
    (1, 1, '2024-10-01 12:00:00', '2024-11-01 12:00:00', FALSE),
    (2, 2, '2024-11-01 12:00:00', NULL, FALSE);

-- Insertion des quittances de loyer (respect du mois et alignement avec occupation/document PDF)
INSERT INTO quittance_loyer (numero_quittance, occupation_id, document_pdf_id, mois_quittance, date_paiement, ville, montant_loyer, montant_charge, remarque, statut_quittance, deleted)
VALUES
    (1, 1, 1, '2024-10', '2024-10-01', 'Rennes', 1200.00, 100.00, 'Paiement reçu à temps', 'PAYEE', FALSE),
    (2, 2, 2, '2024-11', '2024-11-01', 'Rennes', 800.00, 80.00, 'Paiement en avance', 'PAYEE', FALSE);

-- Insertion des séquences pour les quittances
INSERT INTO sequence_quittance (id)
VALUES
    (1),
    (2);
