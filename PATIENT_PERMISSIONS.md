# Implémentation des Permissions PATIENT

## Vue d'ensemble

Cette implémentation ajoute un système de rôles et de permissions complet pour les **PATIENT** dans l'application HealthCare. Tous les utilisateurs (Patient, Médecin, Admin) sont stockés dans la table `UserEntity`.

---

## Relations Établies

### UserEntity ← OneToOne → Patient
- Un patient est lié à un utilisateur unique via la colonne `user_id`
- Un utilisateur de type PATIENT peut accéder à ses propres données

### UserEntity ← OneToOne → Medecine
- Un médecin est lié à un utilisateur unique via la colonne `user_id`
- Un utilisateur de type MEDECIN peut gérer ses informations

---

## PERMISSIONS PATIENT - Ce qu'un patient PEUT faire

### 1. Consulter Son Profil
- **Endpoint**: `GET /api/patients/mon-profil`
- **Rôle Requis**: PATIENT
- **Description**: Le patient peut voir ses propres informations personnelles
- **Exemple de réponse**:
```json
{
  "id": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean@example.com",
  "telephone": "0123456789",
  "dateNaissance": "1990-05-15",
  "totalRendezVous": 3
}
```

### 2. Modifier Son Profil
- **Endpoint**: `PUT /api/patients/modifier-profil`
- **Rôle Requis**: PATIENT
- **Champs Modifiables**: 
  - nom
  - prenom
  - telephone
  - dateNaissance
- **Champs NON Modifiables** (via ce endpoint):
  - email (géré par UserEntity)
  - id (identifiant)
- **Exemple de requête**:
```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "telephone": "0987654321",
  "dateNaissance": "1990-05-15",
  "email": "jean@example.com"
}
```

### 3. Consulter Ses Rendez-vous
- **Endpoint**: `GET /api/rendezVous/mes-rendez-vous`
- **Rôle Requis**: PATIENT
- **Description**: Récupère tous les rendez-vous du patient actuellement connecté
- **Exemple de réponse**:
```json
[
  {
    "id": 1,
    "dateRendezVous": "2026-06-15",
    "statut": "CONFIRME",
    "medecin": { "id": 1, "nom": "Medecin1" },
    "patient": { "id": 1, "nom": "Dupont" }
  },
  {
    "id": 2,
    "dateRendezVous": "2026-07-20",
    "statut": "EN_ATTENTE",
    "medecin": { "id": 2, "nom": "Medecin2" },
    "patient": { "id": 1, "nom": "Dupont" }
  }
]
```

### 4. Consulter Son Dossier Médical
- **Endpoint**: `GET /api/dossierMedical/mon-dossier`
- **Rôle Requis**: PATIENT
- **Description**: Le patient peut consulter son propre dossier médical (diagnostic, observations, etc.)
- **Exemple de réponse**:
```json
{
  "id": 1,
  "diagnostic": "Hypertension",
  "observations": "À suivre régulièrement",
  "dateCreation": "2026-05-01",
  "patient": { "id": 1, "nom": "Dupont" }
}
```

---

## PERMISSIONS PATIENT - Ce qu'un patient NE PEUT PAS faire

### 1. ❌ Consulter les Autres Patients
- Les endpoints comme `GET /api/patients/listerLesPatients` sont **RÉSERVÉS AUX ADMINS**
- Un patient ne peut voir que son propre profil

### 2. ❌ Modifier les Dossiers Médicaux
- Les endpoints suivants sont **RÉSERVÉS AUX MÉDECINS UNIQUEMENT**:
  - `POST /api/dossierMedical/ajouterDiagnostic/{id}`
  - `POST /api/dossierMedical/ajouterObservations/{id}`
- Les patients peuvent **lire** leur dossier, mais **ne peuvent pas le modifier**

### 3. ❌ Gérer les Médecins
- Tous les endpoints de gestion des médecins sont **RÉSERVÉS AUX ADMINS**:
  - `POST /api/medecins/AjouterMedecine` (Admin)
  - `GET /api/medecins/ListerMedecines` (Admin)
  - `PUT /api/medecins/modifierMedecine/{id}` (Admin/Médecin lui-même)
  - `DELETE /api/medecins/supprimerMedecine/{id}` (Admin)

### 4. ❌ Créer/Modifier des Rendez-vous Directement
- Les endpoints suivants sont **RÉSERVÉS AUX ADMINS**:
  - `POST /api/rendezVous/creeUnRendezVous` (création)
  - `PUT /api/rendezVous/modifierRendezVousById/{id}` (modification)
  - `PATCH /api/rendezVous/annulerRendezVous/{id}` (annulation)
- Un patient ne peut que **consulter** ses rendez-vous

---

## Architecture de Sécurité

### Table UserEntity
- Stocke TOUS les utilisateurs du système (Admin, Médecin, Patient)
- Chaque utilisateur a un **rôle unique** (ADMIN, MEDECIN, PATIENT)
- Authentification via JWT Token

```java
@Entity
@Table(name = "user_details")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    
    @Enumerated(value = EnumType.STRING)
    private Role role;  // ADMIN, MEDECIN, PATIENT
}
```

### Relation Patient ← UserEntity
```java
@Entity
public class Patient {
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;  // Lien vers l'utilisateur associé
    // ... autres champs
}
```

### Relation Medecine ← UserEntity
```java
@Entity
public class Medecine {
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;  // Lien vers l'utilisateur associé (médecin)
    // ... autres champs
}
```

---

## Configuration de Sécurité (SecurityConfig)

Les permissions sont définies au niveau des endpoints HTTP:

```java
// Patients peuvent SEULEMENT voir et modifier leur propre profil
.requestMatchers(HttpMethod.GET,"/api/patients/mon-profil").hasRole(Role.PATIENT.name())
.requestMatchers(HttpMethod.PUT,"/api/patients/modifier-profil").hasRole(Role.PATIENT.name())

// Patients peuvent voir leurs rendez-vous
.requestMatchers(HttpMethod.GET,"/api/rendezVous/mes-rendez-vous").hasRole(Role.PATIENT.name())

// Patients peuvent consulter leur dossier médical
.requestMatchers(HttpMethod.GET,"/api/dossierMedical/mon-dossier").hasRole(Role.PATIENT.name())

// Les médecins et admins SEULEMENT peuvent modifier les dossiers
.requestMatchers(HttpMethod.POST,"/api/dossierMedical/ajouterDiagnostic/{id}").hasRole(Role.MEDECIN.name())
.requestMatchers(HttpMethod.POST,"/api/dossierMedical/ajouterObservations/{id}").hasRole(Role.MEDECIN.name())
```

---

## Flux d'Authentification

1. **Utilisateur se connecte** via `/api/auth/login`
   - Username + Password
   - JWT Token retourné

2. **Utilisateur fait une requête** avec le token dans le header `Authorization: Bearer <token>`
   - JwtAuthFilter extrait le token
   - Charge l'utilisateur depuis la base de données
   - Récupère le rôle (PATIENT, MEDECIN, ADMIN)

3. **SecurityConfig vérifie les permissions**
   - Si le rôle n'est pas autorisé → Retour 403 Forbidden
   - Si le rôle est autorisé → Requête traitée

4. **Services vérifient l'accès au niveau métier**
   - `getCurrentUser()` récupère l'utilisateur connecté
   - Compare avec les données à accéder
   - Lance une `ResourceNotFoundException` si accès non autorisé

---

## Exemple de Flux - Patient Consulte Son Dossier

1. Patient s'authentifie:
```bash
POST /api/auth/login
{
  "username": "jean_dupont",
  "password": "password123"
}
```

Réponse:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

2. Patient consule son profil:
```bash
GET /api/patients/mon-profil
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Réponse (200 OK):
```json
{
  "id": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean@example.com",
  "telephone": "0123456789",
  "dateNaissance": "1990-05-15",
  "totalRendezVous": 3
}
```

3. Patient consulte ses rendez-vous:
```bash
GET /api/rendezVous/mes-rendez-vous
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

4. Patient consulte son dossier médical:
```bash
GET /api/dossierMedical/mon-dossier
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## Migration Base de Données

Une nouvelle migration SQL a été créée: `V6__add_user_relation_to_patient_and_medecine.sql`

Cette migration ajoute:
- Colonne `user_id` à la table `patient`
- Colonne `user_id` à la table `medecine`
- Contraintes de clé étrangère vers `user_details`

```sql
ALTER TABLE patient
ADD COLUMN user_id INT,
ADD CONSTRAINT fk_patient_user FOREIGN KEY (user_id) REFERENCES user_details(id);

ALTER TABLE medecine
ADD COLUMN user_id INT,
ADD CONSTRAINT fk_medecine_user FOREIGN KEY (user_id) REFERENCES user_details(id);
```

---

## Fichiers Modifiés

### Modèles (Models)
- ✅ `Patient.java` - Ajout de la relation OneToOne avec UserEntity
- ✅ `Medecine.java` - Ajout de la relation OneToOne avec UserEntity

### Repositories
- ✅ `PatientRepository.java` - Ajout de `findByUser(UserEntity user)`
- ✅ `DossierMedicalRepository.java` - Ajout de `findByPatient(Patient patient)`

### Services
- ✅ `PatientService.java` - Ajout de `getMonProfil()` et `modifierMonProfil()`
- ✅ `RendezVousService.java` - Ajout de `getMesRendezVous()`
- ✅ `DossierMedicalService.java` - Ajout de `getMonDossierMedical()`

### Contrôleurs (Controllers)
- ✅ `PatientController.java` - Ajout des endpoints `/mon-profil` et `/modifier-profil`
- ✅ `RendezVousController.java` - Ajout de l'endpoint `/mes-rendez-vous`
- ✅ `DossierMedicalController.java` - Ajout de l'endpoint `/mon-dossier`

### Configuration
- ✅ `SecurityConfig.java` - Mise à jour des permissions pour tous les roles

### Migration
- ✅ `V6__add_user_relation_to_patient_and_medecine.sql` - Ajout des colonnes user_id

---

## Résumé des Permissions

| Fonctionnalité | ADMIN | MEDECIN | PATIENT |
|---|---|---|---|
| Voir son profil | ✅ (Admin endpoint) | ✅ (via profile endpoint) | ✅ (`/mon-profil`) |
| Modifier profil | ✅ (Admin endpoint) | ✅ | ✅ (`/modifier-profil`) |
| Voir tous les patients | ✅ | ❌ | ❌ |
| Voir les autres patients | ✅ | ❌ | ❌ |
| Voir ses rendez-vous | ✅ | ✅ | ✅ (`/mes-rendez-vous`) |
| Gérer rendez-vous | ✅ | ❌ | ❌ |
| Voir son dossier médical | ✅ | ✅ | ✅ (`/mon-dossier`) |
| Modifier dossier médical | ✅ | ✅ | ❌ |
| Gérer médecins | ✅ | ❌ | ❌ |
| Gérer utilisateurs | ✅ | ❌ | ❌ |

---

## Exécution du Projet

1. **Mettre à jour la base de données**:
   - Flyway exécutera automatiquement la migration V6
   - Les colonnes `user_id` seront ajoutées à `patient` et `medecine`

2. **Compilation et démarrage**:
```bash
mvn clean compile
mvn spring-boot:run
```

3. **Tester les endpoints**:
```bash
# Authentification
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patient1", "password":"password123"}'

# Consulter profil patient
curl -X GET http://localhost:8080/api/patients/mon-profil \
  -H "Authorization: Bearer <token>"

# Consulter rendez-vous patient
curl -X GET http://localhost:8080/api/rendezVous/mes-rendez-vous \
  -H "Authorization: Bearer <token>"

# Consulter dossier médical patient
curl -X GET http://localhost:8080/api/dossierMedical/mon-dossier \
  -H "Authorization: Bearer <token>"
```

---

## Notes Importantes

1. ✅ **Universal User Storage**: Tous les utilisateurs sont stockés dans `UserEntity`
2. ✅ **Role-Based Access Control**: Utilisation de Spring Security et JWT
3. ✅ **Patient Isolation**: Les patients ne voient que leurs propres données
4. ✅ **Proper Relations**: Relations OneToOne entre User ← Patient et User ← Medecine
5. ✅ **Service-Level Validation**: Vérification du propriétaire au niveau du service
6. ✅ **Database Migration**: Flyway gère automatiquement le schéma de base de données


