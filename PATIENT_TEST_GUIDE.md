# Guide de Test - Endpoints Patient

Ce guide explique comment tester les nouveaux endpoints implémentés pour les patients.

## Prérequis

- Application HealthCare en cours d'exécution
- Un utilisateur patient enregistré dans la base de données
- JWT Token pour authentification

## Étapes de Test

### 1. Authentification du Patient

**URL**: `POST http://localhost:8080/api/auth/login`

**Corps (JSON)**:
```json
{
  "username": "patient1",
  "password": "password"
}
```

**Réponse Attendue** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTYyMzQ0NzgyOH0.signature"
}
```

Sauvegardez le token retourné pour les requêtes suivantes.

---

### 2. Consulter le Profil du Patient

**URL**: `GET http://localhost:8080/api/patients/mon-profil`

**Headers**:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```

**Réponse Attendue** (200 OK):
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

**Codes d'erreur possibles**:
- 401 Unauthorized: Token invalide ou expiré
- 404 Not Found: Profil patient non trouvé pour l'utilisateur connecté

---

### 3. Modifier le Profil du Patient

**URL**: `PUT http://localhost:8080/api/patients/modifier-profil`

**Headers**:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```

**Corps (JSON)**:
```json
{
  "nom": "Dupont",
  "prenom": "Jean-Michel",
  "email": "jean@example.com",
  "telephone": "0987654321",
  "dateNaissance": "1990-05-15"
}
```

**Réponse Attendue** (200 OK):
```json
{
  "id": 1,
  "nom": "Dupont",
  "prenom": "Jean-Michel",
  "email": "jean@example.com",
  "telephone": "0987654321",
  "dateNaissance": "1990-05-15",
  "totalRendezVous": 3
}
```

**Points importants**:
- Le patient peut modifier: nom, prenom, telephone, dateNaissance
- L'email n'est pas modifiable via ce endpoint (géré par UserEntity)
- Les champs vides doivent être fournis mais ne seront pas modifiés s'ils sont null

---

### 4. Consulter Ses Rendez-vous

**URL**: `GET http://localhost:8080/api/rendezVous/mes-rendez-vous`

**Headers**:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```

**Réponse Attendue** (200 OK):
```json
[
  {
    "id": 1,
    "dateRendezVous": "2026-06-15",
    "statut": "CONFIRME",
    "medecin": {
      "id": 1,
      "nom": "Medecin1",
      "specialite": "Cardiologue"
    },
    "patient": {
      "id": 1,
      "nom": "Dupont"
    }
  },
  {
    "id": 2,
    "dateRendezVous": "2026-07-20",
    "statut": "EN_ATTENTE",
    "medecin": {
      "id": 2,
      "nom": "Medecin2",
      "specialite": "Dermatologue"
    },
    "patient": {
      "id": 1,
      "nom": "Dupont"
    }
  }
]
```

**Codes d'erreur possibles**:
- 401 Unauthorized: Token invalide ou expiré
- 404 Not Found: Profil patient non trouvé

**Notes**:
- Retourne uniquement les rendez-vous du patient authentifié
- Les patients ne peuvent pas voir les rendez-vous des autres patients
- Liste triée par date si applicable

---

### 5. Consulter Son Dossier Médical

**URL**: `GET http://localhost:8080/api/dossierMedical/mon-dossier`

**Headers**:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```

**Réponse Attendue** (200 OK):
```json
{
  "id": 1,
  "diagnostic": "Hypertension artérielle",
  "observations": "À suivre régulièrement. Refaire analyses sanguin dans 3 mois.",
  "dateCreation": "2026-05-01",
  "patient": {
    "id": 1,
    "nom": "Dupont",
    "prenom": "Jean"
  }
}
```

**Codes d'erreur possibles**:
- 401 Unauthorized: Token invalide ou expiré
- 404 Not Found: 
  - Profil patient non trouvé
  - Dossier médical non trouvé pour ce patient

**Points importants**:
- Le patient ne peut voir que SON dossier médical
- Les patients ne peuvent pas modifier le diagnostic ou observations
- Seuls les médecins peuvent ajouter/modifier le diagnostic et observations

---

## Tests de Sécurité - Vérifier les Restrictions

### Test 1: Patient ne peut pas voir les autres patients

**URL Tentée**: `GET http://localhost:8080/api/patients/listerLesPatients`

**Réponse Attendue** (403 Forbidden):
```json
{
  "error": "Forbidden",
  "message": "Vous n'avez pas les permissions requises"
}
```

---

### Test 2: Patient ne peut pas modifier le dossier médical

**URL Tentée**: `POST http://localhost:8080/api/dossierMedical/ajouterDiagnostic/1`

**Corps**:
```json
"Patient essaie de modifier le diagnostic"
```

**Réponse Attendue** (403 Forbidden):
```json
{
  "error": "Forbidden",
  "message": "Vous n'avez pas les permissions requises"
}
```

**Points**: Seuls les rôles MEDECIN peuvent modifier le dossier médical

---

### Test 3: Patient ne peut pas gérer les médecins

**URL Tentée**: `GET http://localhost:8080/api/medecins/ListerMedecines`

**Réponse Attendue** (403 Forbidden):
```json
{
  "error": "Forbidden",
  "message": "Vous n'avez pas les permissions requises"
}
```

---

### Test 4: Patient avec Token Invalide

**URL**: `GET http://localhost:8080/api/patients/mon-profil`

**Headers with Invalid Token**:
```
Authorization: Bearer invalid_token_12345
```

**Réponse Attendue** (401 Unauthorized):
```json
{
  "error": "Unauthorized",
  "message": "Token invalide ou expiré"
}
```

---

## Commandes cURL pour Testing

### 1. Authentification
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patient1","password":"password"}'
```

### 2. Voir le Profil
```bash
curl -X GET http://localhost:8080/api/patients/mon-profil \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json"
```

### 3. Modifier le Profil
```bash
curl -X PUT http://localhost:8080/api/patients/modifier-profil \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nom":"Dupont","prenom":"Jean","telephone":"0987654321","dateNaissance":"1990-05-15","email":"jean@example.com"}'
```

### 4. Voir les Rendez-vous
```bash
curl -X GET http://localhost:8080/api/rendezVous/mes-rendez-vous \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json"
```

### 5. Voir le Dossier Médical
```bash
curl -X GET http://localhost:8080/api/dossierMedical/mon-dossier \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json"
```

### 6. Tester Restriction - Voir Autres Patients (403)
```bash
curl -X GET http://localhost:8080/api/patients/listerLesPatients \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json"
```

---

## Postman Collection

Pour importer dans Postman, créez une nouvelle collection avec les requêtes suivantes:

### Variables d'Environnement
```
BASE_URL: http://localhost:8080
TOKEN: (rempli après authentification)
```

### Requests

**Request 1: Login**
- Method: POST
- URL: `{{BASE_URL}}/api/auth/login`
- Body: `{"username":"patient1","password":"password"}`
- Tests: `pm.environment.set("TOKEN", pm.response.json().token);`

**Request 2: Get Profile**
- Method: GET
- URL: `{{BASE_URL}}/api/patients/mon-profil`
- Headers: `Authorization: Bearer {{TOKEN}}`

**Request 3: Update Profile**
- Method: PUT
- URL: `{{BASE_URL}}/api/patients/modifier-profil`
- Headers: `Authorization: Bearer {{TOKEN}}`
- Body: RAW JSON avec les données à modifier

**Request 4: Get Appointments**
- Method: GET
- URL: `{{BASE_URL}}/api/rendezVous/mes-rendez-vous`
- Headers: `Authorization: Bearer {{TOKEN}}`

**Request 5: Get Medical File**
- Method: GET
- URL: `{{BASE_URL}}/api/dossierMedical/mon-dossier`
- Headers: `Authorization: Bearer {{TOKEN}}`

---

## Codes de Réponse HTTP

| Code | Signification |
|---|---|
| 200 | OK - Succès de la requête |
| 400 | Bad Request - Données invalides |
| 401 | Unauthorized - Token invalide/expiré |
| 403 | Forbidden - Permissions insuffisantes |
| 404 | Not Found - Ressource non trouvée |
| 500 | Internal Server Error - Erreur serveur |

---

## Dépannage

### Le token expire après quelques heures
- Solution: Récupérer un nouveau token en se réauthentifiant

### Erreur "Profil patient non trouvé"
- Assurez-vous que l'utilisateur est lié à un patient dans la structure de données
- Vérifiez que la colonne `user_id` est remplie dans la table `patient`

### Erreur "Dossier médical non trouvé"
- Le patient doit avoir un dossier médical créé dans la base
- Vérifiez que `patient_id` est correctement rempli dans la table `dossier_medical`

---

## Notes de Sécurité

✅ **Isolation des Données**: Chaque patient ne peut accéder qu'à ses propres données
✅ **Authentification JWT**: Tokens sécurisés avec expiration
✅ **Role-Based Access Control**: Permissions basées sur les rôles
✅ **Service-Level Validation**: Double vérification des permissions au niveau du service
✅ **Database-Level Constraints**: Contraintes de clé étrangère dans la base de données


