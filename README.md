# Gestion Quittance Appart

## 1. Présentation du projet

Gestion Quittance Appart est une application backend Spring Boot multi-modules dédiée à la gestion des quittances d’appartement et à l’authentification des utilisateurs. Ce projet illustre une architecture professionnelle, modulaire et évolutive, respectant les standards du développement Java moderne.

## 2. Architecture du projet

Le projet est structuré en plusieurs modules Maven, favorisant la séparation des responsabilités et la maintenabilité :

- **services-metier/** :
  - `auth-service` : gestion de l’authentification, utilisateurs, rôles, 2FA, JWT, etc.
  - `quittance-service` : gestion des quittances, appartements, propriétaires, locataires, etc.
- **services-techniques/** :
  - `common-utils` : utilitaires partagés (enums, mappers, helpers, etc.)
  - `config` : configuration centralisée des services métier.
- **open-api/** :
  - Spécifications OpenAPI YAML pour la documentation et la génération de clients/serveurs (`auth-service-api.yaml`, `gestion-quittances-api.yaml`).
- **docker/** :
  - Fichiers Docker et Docker Compose pour le déploiement local (base de données MariaDB, configuration personnalisée).
- **data/** :
  - Scripts SQL pour l’initialisation de la base de données.

### Points clés de l’architecture
- **Multi-modules Maven** : chaque module est indépendant et géré via le parent `pom.xml`.
- **dependencyManagement & BOM** : gestion centralisée des versions via Spring Boot BOM et autres BOM pour garantir la cohérence des dépendances.
- **Intégration Spring Cloud** : gestion de la configuration distribuée et des microservices.
- **OpenAPI Generator** : génération automatique de clients et serveurs à partir des spécifications OpenAPI.
- **Gestion JWT** : sécurisation des endpoints via tokens JWT.
- **Java 17** : utilisation des dernières fonctionnalités du langage.

## 3. Stack technique

- **Java** : 17
- **Spring Boot** : 3.2.2
- **Spring Cloud** : 2023.0.0
- **MapStruct** : 1.5.5.Final
- **Lombok** : 1.18.30
- **JWT** : 0.11.5 (jjwt)
- **SpringDoc OpenAPI** : 2.3.0
- **OpenAPI Generator** : 7.2.0
- **Maven** : gestionnaire de dépendances
- **Spring Data JPA** : accès base de données
- **MariaDB / PostgreSQL** : base de données (configurable)
- **Docker / Docker Compose** : déploiement local
- **SLF4J** : logging
- **JUnit 5 & Mockito** : tests unitaires

## 4. Instructions d’installation locale

### Prérequis
- Java 17
- Maven 3.8+
- Docker (pour le lancement via conteneurs)

### Installation
1. Cloner le dépôt :
   ```bash
   git clone git@github.com:kendryk/Gestion-quittance-appart.git
   cd Gestion-quittance-appart
   ```
2. Configurer les variables d’environnement nécessaires (voir section "Configuration requise").
3. Installer les dépendances et compiler :
   ```bash
   mvn clean install
   ```

## 5. Commandes Maven utiles

- Compiler le projet :
  ```bash
  mvn clean compile
  ```
- Lancer les tests unitaires :
  ```bash
  mvn test
  ```
- Packager le projet :
  ```bash
  mvn clean package
  ```
- Démarrer un service :
  ```bash
  mvn spring-boot:run -pl services-techniques/config
  mvn spring-boot:run -pl services-metier/auth-service
  mvn spring-boot:run -pl services-metier/quittance-service
  ```

## 6. Lancement via Docker

1. Vérifier que Docker et Docker Compose sont installés.
2. Lancer l’environnement complet :
   ```bash
   docker-compose -f docker/docker-compose.yml up --build
   ```
3. Les services seront accessibles sur les ports configurés dans `docker-compose.yml`.

## 7. Configuration requise

- **Java** : 17
- **Variables d’environnement** à définir selon le service :
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `SPRING_PROFILES_ACTIVE` (optionnel)
- **Fichiers de configuration** :
  - `src/main/resources/application.yml` ou `application.properties` pour chaque service
  - Configurations spécifiques dans `services-techniques/config/src/main/resources/config/`

## 8. Documentation API

Les spécifications OpenAPI sont disponibles dans le dossier `open-api/` :
- `auth-service-api.yaml` : endpoints d’authentification, gestion des utilisateurs, sécurité
- `gestion-quittances-api.yaml` : endpoints de gestion des quittances, appartements, propriétaires, locataires

Pour visualiser la documentation :
- Utiliser Swagger UI ou Redoc en local
- Ou importer les fichiers YAML dans un outil compatible (ex : [Swagger Editor](https://editor.swagger.io/))

## 9. Structure des modules expliquée

- **controller/** : expose les endpoints REST, sans logique métier
- **service/** : contient la logique métier, injection par constructeur
- **repository/** : interfaces Spring Data JPA pour l’accès aux données
- **domain/model/** : entités JPA (jamais exposées directement)
- **dto/** : objets de transfert (DTO)
- **mapper/** : MapStruct ou mappers dédiés pour conversion entité <-> DTO
- **exception/** : gestion des exceptions métier
- **config/** : configuration Spring, sécurité, beans
- **infrastructure/** : adaptateurs, entités, mappers techniques
- **security/** : gestion JWT, filtres, utilitaires de sécurité

## 10. Bonnes pratiques et conventions utilisées

- Respect des principes SOLID
- Injection par constructeur (jamais par champ)
- Architecture en couches (controller, service, repository)
- Logique métier uniquement dans les services
- Utilisation systématique de DTO pour exposer les données
- Mapping via MapStruct ou mappers dédiés
- Logging avec SLF4J (jamais System.out.println)
- Tests unitaires avec JUnit 5 et Mockito (pas de @SpringBootTest pour les tests unitaires)
- Configuration externalisée et centralisée
- Utilisation de BOM et dependencyManagement pour la cohérence des versions
- Sécurisation des APIs via JWT
- Documentation et génération de code via OpenAPI Generator

---

## Points techniques intéressants

- **Architecture multi-modules Maven** : séparation claire des responsabilités, favorise la scalabilité et la maintenabilité.
- **Gestion centralisée des dépendances** : usage du BOM Spring Boot et dependencyManagement pour éviter les conflits de versions.
- **Intégration Spring Cloud** : configuration distribuée, préparation à l’orchestration de microservices.
- **OpenAPI Generator** : génération automatique de clients et serveurs, documentation synchronisée avec le code.
- **Sécurité JWT** : authentification stateless, gestion fine des rôles et permissions.
- **Utilisation avancée de MapStruct** : mapping performant entre entités et DTOs.
- **Tests unitaires robustes** : JUnit 5, Mockito, respect des bonnes pratiques de test.

## Compétences démontrées

- Maîtrise de l’architecture Spring Boot multi-modules
- Gestion avancée des dépendances Maven (BOM, dependencyManagement)
- Implémentation de la sécurité avec JWT
- Documentation et génération de code via OpenAPI
- Utilisation de MapStruct pour le mapping
- Mise en place de tests unitaires professionnels
- Déploiement et orchestration via Docker Compose
- Respect des standards Java 17 et des principes SOLID

## Évolutions possibles

- Ajout d’un module de gestion des notifications (email, SMS)
- Intégration d’un service de paiement en ligne
- Mise en place d’un monitoring (Spring Boot Actuator, Prometheus, Grafana)
- Déploiement sur Kubernetes
- Ajout d’une interface front-end (Angular, React)
- Extension des APIs pour la gestion multi-propriétaires
- Authentification OAuth2 / OpenID Connect
- Internationalisation (i18n)

---

> **À compléter :**
> - URL du dépôt Git
> - Ports exposés par les services
> - Variables d’environnement spécifiques à chaque service
> - Informations sur la licence et les auteurs
