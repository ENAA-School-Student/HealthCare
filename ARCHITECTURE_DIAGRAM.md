# Architecture Visuelle - Système d'Authentification et Permissions Patient

## 1. Diagramme ERD (Entité-Relation)

```
┌─────────────────────────────────────────────────────────────────┐
│                      ARCHITECTURE SYSTÈME                       │
└─────────────────────────────────────────────────────────────────┘

                         ┌─────────────────┐
                         │   UserEntity    │
                         │ (user_details)  │
                         ├─────────────────┤
                         │ - id [PK]       │
                         │ - username      │
                         │ - email         │
                         │ - password      │
                         │ - role          │◄──────┬────────────────────┐
                         │           (ADMIN,│       │                    │
                         │             MEDECIN,     │                    │
                         │             PATIENT)    │                    │
                         └────────┬────────┘        │                    │
                                  │                 │                    │
                    ┌─────────────┴─────────────┐   │                    │
                    │ OneToOne                  │   │                    │
                    │ (user_id)                 │   │                    │
                    │                           │   │                    │
           ┌────────▼───────────┐   ┌──────────▼──┴──────┐   ┌──────────▼────────┐
           │     Patient        │   │     Medecine       │   │    Admin           │
           ├────────────────────┤   ├────────────────────┤   │  (géré via User)   │
           │ - id [PK]          │   │ - id [PK]          │   │                    │
           │ - nom              │   │ - nom              │   │ Permissions:       │
           │ - prenom           │───│ - telephone        │   │ ✓ Gérer tout      │
           │ - email            │   │ - email            │   │ ✓ Voir tout        │
           │ - telephone        │   │ - specialite       │   │ ✓ Modifier tout    │
           │ - dateNaissance    │   │ - user_id (FK)     │   │                    │
           │ - user_id (FK)     │   └────────────────────┘   └────────────────────┘
           └────────┬───────────┘
                    │
        ┌───────────┴────────────┐
        │ OneToMany              │
        │ (patient_id)           │
        │                        │
   ┌────▼──────────┐  ┌─────────▼───────┐
   │  RenderVous   │  │ DossierMedical  │
   ├───────────────┤  ├─────────────────┤
   │ - id [PK]     │  │ - id [PK]       │
   │ - dateRV      │  │ - diagnostic    │
   │ - statut      │  │ - observations  │
   │ - patient_id  │  │ - dateCreation  │
   │ - medecine_id │  │ - patient_id    │
   └───────────────┘  └─────────────────┘
```

---

## 2. Flux d'Authentification et Autorisation

```
┌────────────────────────────────────────────────────────────────────────┐
│                    FLUX D'AUTHENTIFICATION ET AUTORISATION             │
└────────────────────────────────────────────────────────────────────────┘

1. CLIENT (Frontend)
   └─► POST /api/auth/login
       {
         "username": "patient1",
         "password": "password123"
       }

2. SERVEUR - AuthService
   └─► Valide credentials
   └─► Charge UserEntity depuis la base
   └─► Récupère le rôle (PATIENT, MEDECIN, ADMIN)
   └─► Génère JWT Token avec le rôle

3. JWT TOKEN GÉNÉRÉ
   └─► Payload contient:
       {
         "sub": "patient1",
         "role": "PATIENT",
         "iat": 1623447828,
         "exp": 1623534228
       }

4. CLIENT STOCK LE TOKEN
   └─► Envoie le token dans tous les headers Authorization: Bearer <token>

5. REQUÊTE PROTÉGÉE
   └─► GET /api/patients/mon-profil
   └─► Authorization: Bearer eyJhbGciOiJIUzI1NiIsIn...

6. JWTAUTHFILTER (OncePerRequestFilter)
   └─► Extrait le token du header Authorization
   └─► Valide la signature et l'expiration
   └─► Charge l'UserDetails depuis UserService
   └─► Place dans SecurityContext
   
7. SECURITYCONFIG (HttpSecurity)
   ├─► Vérifie le pattern d'URL
   ├─► Récupère le rôle du SecurityContext
   ├─► Confronte avec hasRole() ou hasAnyRole()
   ├─► SI AUTORISÉ: laisse passer (continue chaîne) ✓
   └─► SI REFUSÉ: retourne 403 Forbidden ✗

8. SERVICE (PatientService, RendezVousService, etc.)
   └─► Récupère getCurrentUser() du SecurityContext
   └─► Valide que l'utilisateur ne peut accéder qu'à SES données
   └─► SI OUI: retourne la ressource ✓
   └─► SI NON: lance ResourceNotFoundException (404) ✗

9. RÉPONSE AU CLIENT
   └─► 200 OK avec les données (si autorisé)
   └─► 403 Forbidden (rôle insuffisant)
   └─► 404 Not Found (données d'autres utilisateurs)
   └─► 401 Unauthorized (token invalide/expiré)
```

---

## 3. Cycle de Vie d'une Requête Patient

```
┌───────────────────────────────────────────────────────────────────┐
│              CYCLE DE VIE: GET /api/patients/mon-profil           │
└───────────────────────────────────────────────────────────────────┘

REQUEST: GET /api/patients/mon-profil
         Authorization: Bearer valid_token
         ↓
    ┌─────────────────────────────────────┐
    │  Step 1: JwtAuthFilter              │
    ├─────────────────────────────────────┤
    │ ✓ Extrait "Bearer token"            │
    │ ✓ Valide signature                  │
    │ ✓ Extraits username du token        │
    │ ✓ Charge UserDetailsById username   │
    │ ✓ Place dans SecurityContext        │
    └──────────────────┬──────────────────┘
                       ↓
    ┌─────────────────────────────────────┐
    │  Step 2: DispatcherServlet          │
    ├─────────────────────────────────────┤
    │ Mappe vers PatientController        │
    │ Appelle getMonProfil()              │
    └──────────────────┬──────────────────┘
                       ↓
    ┌─────────────────────────────────────────────────────────┐
    │  Step 3: PatientController                              │
    ├─────────────────────────────────────────────────────────┤
    │ @GetMapping("/mon-profil")                              │
    │ @Secured("ROLE_PATIENT") // Vérification Spring        │
    │ public PatientResponseDTO getMonProfil() {              │
    │   return patientService.getMonProfil();                 │
    │ }                                                       │
    │                                                         │
    │ ✓ Rôle = PATIENT (?                                    │
    │ ✓ Autorisé → Continue                                  │
    └──────────────────┬──────────────────────────────────────┘
                       ↓
    ┌───────────────────────────────────────────────────────────────┐
    │  Step 4: PatientService.getMonProfil()                        │
    ├───────────────────────────────────────────────────────────────┤
    │ 1. UserEntity currentUser = getCurrentUser()                   │
    │    → Récupère de SecurityContext.getAuthentication().getName()│
    │                                                               │
    │ 2. Patient patient = patientRepository.findByUser(currentUser)│
    │    → Récupère le patient associé à cet utilisateur           │
    │    → Si non trouvé: lance ResourceNotFoundException          │
    │                                                               │
    │ 3. PatientResponseDTO dto = patientMapper.toDto(patient)      │
    │    → Mappe l'entité vers le DTO                              │
    │                                                               │
    │ 4. return dto                                                 │
    └──────────────────┬───────────────────────────────────────────┘
                       ↓
    ┌──────────────────────────────────────┐
    │  Step 5: Response Sent to Client     │
    ├──────────────────────────────────────┤
    │ HTTP/1.1 200 OK                      │
    │ Content-Type: application/json       │
    │                                      │
    │ {                                    │
    │   "id": 1,                           │
    │   "nom": "Dupont",                   │
    │   "prenom": "Jean",                  │
    │   "email": "...",                    │
    │   "telephone": "...",                │
    │   "dateNaissance": "1990-05-15",     │
    │   "totalRendezVous": 3               │
    │ }                                    │
    └──────────────────────────────────────┘
```

---

## 4. Matrice de Contrôle d'Accès

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   ACCESS CONTROL MATRIX (ACM)                           │
└─────────────────────────────────────────────────────────────────────────┘

RESOURCE:                          ADMIN    MEDECIN   PATIENT
────────────────────────────────────────────────────────────
Patients - Lister All              ✓        ✗         ✗
Patients - Mon Profil              ✓        N/A       ✓*
Patients - Modifier Mon Profil     ✓        N/A       ✓*
Rendez-vous - Lister All           ✓        ✓         ✗
Rendez-vous - Mes Rendez-vous      ✓        ✓         ✓*
Rendez-vous - Créer                ✓        ✗         ✗
Rendez-vous - Modifier             ✓        ✗         ✗
Rendez-vous - Annuler              ✓        ✗         ✗
Dossier Médical - Lister All       ✓        ✗         ✗
Dossier Médical - Mon Dossier      ✓        ✓         ✓*
Dossier Médical - Créer            ✓        ✗         ✗
Dossier Médical - Ajouter Diagnostic  ✓    ✓         ✗
Dossier Médical - Ajouter Observations ✓   ✓         ✗
Médecins - Gérer                   ✓        ✗*        ✗
────────────────────────────────────────────────────────────

Légende:
✓   = Droits d'accès complets
✗   = Accès refusé
*   = Accès limité à ses propres données
N/A = Non applicable (rôle n'a pas ce cas d'usage)

PATIENT RESTRICTIONS:
- * Profil: Peut uniquement voir/modifier SON profil (pas les autres)
- * Rendez-vous: Peut uniquement voir SES rendez-vous
- * Dossier: Peut uniquement voir SON dossier (pas modifier)
```

---

## 5. Structure de Réponses Sécurisées

```
┌──────────────────────────────────────────────────────────────────┐
│              VALIDATIONS DE SÉCURITÉ SUPERPOSÉES                 │
└──────────────────────────────────────────────────────────────────┘

REQUÊTE: GET /api/patients/mon-profil
USER: patient1 (ID: 1, Role: PATIENT)

VALIDATION 1: URL/ROLE MATCHING (Spring Security)
├─ URL: /api/patients/mon-profil
├─ Handler: getMonProfil()
├─ Rôle requis: PATIENT
├─ Rôle utilisateur: PATIENT ✓
└─ RÉSULTAT: Continue

VALIDATION 2: SERVICE-LEVEL DATA ISOLATION (Business Logic)
├─ Récupère currentUser: UserEntity(username="patient1", id=1)
├─ Query: findByUser(currentUser)
├─ Récupère: Patient(id=1, user_id=1) ✓
├─ Validation: patient.getUser().getId() == currentUser.getId() ✓
└─ RÉSULTAT: Autorisé de voir ces données

ATTAQUE POTENTIELLE 1: Try to see another patient's profile
GET /api/patients/consulterPatientPar/2  (Patient ID=2)
├─ Role Check: ADMIN required
├─ Patient1 role: PATIENT
└─ RÉSULTAT: 403 FORBIDDEN

ATTAQUE POTENTIELLE 2: Try to modify someone else's profile
PUT /api/patients/modifier-profil (But actually other's data)
├─ Role Check: PATIENT required ✓
├─ Get currentUser: patient1 (ID=1)
├─ Query: findByUser(patient1) → Patient(id=1)
├─ Attempted data: contains patient2 info
├─ Service Validation: La requête modifie toujours patient1
└─ RÉSULTAT: 200 OK but patient1's data only

ATTAQUE POTENTIELLE 3: Expired/Invalid Token
GET /api/patients/mon-profil
Authorization: Bearer expired_or_invalid_token
├─ JwtAuthFilter validates token
├─ Token expired or invalid detected
├─ No SecurityContext set
└─ RÉSULTAT: 401 UNAUTHORIZED
```

---

## 6. Flux de Vérification des Permissions Détaillé

```
┌────────────────────────────────────────────────────────────────┐
│          DETAILED PERMISSION CHECK FLOW                        │
└────────────────────────────────────────────────────────────────┘

PATIENT REQUEST: "Je veux voir mon profil"

├─► LAYER 1: JwtAuthFilter
│   ├─ Token: valid_jwt_token
│   ├─ Action: Extract & validate
│   ├─ Result: SecurityContext = UserDetails(username=patient1, role=PATIENT)
│   └─ Status: ✓ PASS
│
├─► LAYER 2: SecurityConfig HttpSecurity
│   ├─ Path: /api/patients/mon-profil
│   ├─ Method: GET
│   ├─ Required Role: PATIENT
│   ├─ User Role: PATIENT
│   ├─ Compare: PATIENT == PATIENT?
│   └─ Status: ✓ PASS (let through)
│
├─► LAYER 3: PatientController
│   ├─ Handler: getMonProfil()
│   ├─ Annotation: @GetMapping("/mon-profil")
│   ├─ Endpoint: Accessible
│   └─ Status: ✓ PASS (invoke method)
│
└─► LAYER 4: PatientService
    ├─ Method: getMonProfil()
    ├─ Step 1: UserEntity currentUser = getCurrentUser()
    │          Extract from SecurityContext → patient1
    │
    ├─ Step 2: Patient patient = patientRepository.findByUser(currentUser)
    │          Query: SELECT * FROM patient WHERE user_id = 1
    │          Result: Patient(id=1, user_id=1) ✓
    │
    ├─ Step 3: Validate ownership
    │          if (patient == null || patient.getUser().getId() != currentUser.getId())
    │          Result: Patient exists and belongs to current user ✓
    │
    ├─ Step 4: Map and return
    │          return patientMapper.toDto(patient)
    │          Response: PatientResponseDTO with patient1's data
    │
    └─ Status: ✓ PASS (return data)

RESPONSE: 200 OK with patient1's profile data

═══════════════════════════════════════════════════════════════

MALICIOUS REQUEST: "Je veux voir le profil du patient 2"

├─► LAYER 1: JwtAuthFilter
│   ├─ Token: valid_jwt_token_for_patient1
│   ├─ Action: Extract & validate
│   ├─ Result: SecurityContext = UserDetails(username=patient1, role=PATIENT)
│   └─ Status: ✓ PASS
│
├─► LAYER 2: SecurityConfig HttpSecurity
│   ├─ Path: /api/patients/listerLesPatients
│   ├─ Method: GET
│   ├─ Required Role: ADMIN
│   ├─ User Role: PATIENT
│   ├─ Compare: PATIENT == ADMIN?
│   └─ Status: ✗ FAIL (access denied)
│
└─► Response: 403 FORBIDDEN
    "You do not have permission to access this resource"

ATTACK BLOCKED AT LAYER 2 (HTTP Level)
```

---

## 7. Exemple de Données dans la Base

```
┌────────────────────────────────────────────────────────────────┐
│                  EXEMPLE DE DONNÉES - BDD                      │
└────────────────────────────────────────────────────────────────┘

TABLE: user_details
║ id │ username   │ email              │ password_hash  │ role   ║
╠════╪════════════╪════════════════════╪════════════════╪════════╣
║ 1  │ admin1     │ admin@hc.fr        │ hash(pass)     │ ADMIN  ║
║ 2  │ medecin1   │ medecin@hc.fr      │ hash(pass)     │ MEDECIN║
║ 3  │ patient1   │ patient@hc.fr      │ hash(pass)     │ PATIENT║
║ 4  │ patient2   │ patient2@hc.fr     │ hash(pass)     │ PATIENT║
╚════╧════════════╧════════════════════╧════════════════╧════════╝

TABLE: patient
║ id │ nom    │ prenom │ email        │ telephone │ user_id ║
╠════╪════════╪════════╪══════════════╪═══════════╪═════════╣
║ 1  │ Dupont │ Jean   │ j.dupont@... │ 0123456789│ 3       ║
║ 2  │ Martin │ Pierre │ p.martin@... │ 0987654321│ 4       ║
╚════╧════════╧════════╧══════════════╧═══════════╧═════════╝

TABLE: medecine
║ id │ nom            │ specialite │ email          │ user_id ║
╠════╪════════════════╪════════════╪════════════════╪═════════╣
║ 1  │ Dr. Medecin1   │ Cardio     │ medecin@...    │ 2       ║
╚════╧════════════════╧════════════╧════════════════╧═════════╝

TABLE: render_vous
║ id │ date_rendez_vous │ statut     │ medecine_id │ patient_id ║
╠════╪══════════════════╪════════════╪═════════════╪════════════╣
║ 1  │ 2026-06-15       │ CONFIRME   │ 1           │ 1          ║
║ 2  │ 2026-07-20       │ EN_ATTENTE │ 1           │ 1          ║
║ 3  │ 2026-06-20       │ CONFIRME   │ 1           │ 2          ║
╚════╧══════════════════╧════════════╧═════════════╧════════════╝

TABLE: dossier_medical
║ id │ diagnostic      │ observations │ date_creation │ patient_id ║
╠════╪═════════════════╪══════════════╪═══════════════╪════════════╣
║ 1  │ Hypertension    │ À suivre     │ 2026-05-01    │ 1          ║
║ 2  │ Diabète         │ Type 2       │ 2026-04-15    │ 2          ║
╚════╧═════════════════╧══════════════╧═══════════════╧════════════╝

SCENARIO D'ACCÈS:
├─ User: patient1 (id=3)
│  ├─ CAN see: Patient(id=1).data where patient.user_id=3
│  ├─ CAN see: RenderVous(id=1,2) where patient_id=1
│  ├─ CAN see: DossierMedical(id=1) where patient_id=1
│  ├─ CANNOT see: Patient(id=2) - different user
│  └─ CANNOT see: RenderVous(id=3) - belongs to patient_id=2
│
└─ User: admin1 (id=1)
   ├─ CAN see: ALL patients' data
   ├─ CAN see: ALL rendez-vous
   ├─ CAN see: ALL dossiers médicaux
   └─ CAN modify: EVERYTHING
```

---

## 8. Diagramme de Sécurité en Couches

```
┌─────────────────────────────────────────────────────────────────┐
│                    3-LAYER SECURITY MODEL                       │
└─────────────────────────────────────────────────────────────────┘

                        INCOMING REQUEST
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│ LAYER 1: JWT AUTHENTICATION (JwtAuthFilter)                     │
├──────────────────────────────────────────────────────────────────┤
│ • Valide le token JWT                                            │
│ • Charge l'utilisateur depuis la BD                             │
│ • Place dans SecurityContext                                     │
│ • Bloque: 401 Unauthorized (token invalide/expiré)             │
└──────────────────┬───────────────────────────────────────────────┘
                   ↓
┌──────────────────────────────────────────────────────────────────┐
│ LAYER 2: ROLE-BASED HTTP AUTHORIZATION (SecurityConfig)         │
├──────────────────────────────────────────────────────────────────┤
│ • Vérifie le rôle vs l'URL demandée                             │
│ • Utilise hasRole(), hasAnyRole(), etc.                         │
│ • Bloque: 403 Forbidden (rôle insuffisant)                      │
└──────────────────┬───────────────────────────────────────────────┘
                   ↓
┌──────────────────────────────────────────────────────────────────┐
│ LAYER 3: BUSINESS LOGIC DATA ISOLATION (Services)               │
├──────────────────────────────────────────────────────────────────┤
│ • Récupère l'utilisateur current via SecurityContext            │
│ • Valide que les données demandées appartiennent à l'utilisateur│
│ • Rejette l'accès à d'autres utilisateurs' données             │
│ • Bloque: 404 Not Found (accès refusé au niveau métier)        │
└──────────────────┬───────────────────────────────────────────────┘
                   ↓
                SUCCESS: Return data to CLIENT

═════════════════════════════════════════════════════════════════

EXEMPLE ATTAQUE - PATIENT2 TRIES TO ACCESS PATIENT1's DATA:

Patient2 (role=PATIENT, user_id=4) tries:
GET /api/patients/mon-profil?patient_id=1

Step 1: JWT Auth
├─ Token valid ✓
├─ User = patient2 ✓
└─ PASS → Continue

Step 2: HTTP Authorization
├─ URL: /api/patients/mon-profil
├─ Required role: PATIENT
├─ User role: PATIENT ✓
└─ PASS → Continue (endpoint is for PATIENT role)

Step 3: Business Logic (THIS IS WHERE IT'S BLOCKED)
├─ getCurrentUser() = patient2 (id=4)
├─ findByUser(patient2) = fetch Patient with user_id=4
├─ Patient fetched has id=2 (not 1!)
├─ Trying to pass patient_id=1 is IGNORED
├─ Always returns patient2's data (id=2)
└─ BLOCK → Returns patient2's data only

RESULT: 200 OK (but patient2 sees THEIR OWN data, not patient1's)
SECURITY: ✓ MAINTAINED (data isolation at service level)
```

---

## Résumé de la Sécurité

```
✅ 3 COUCHES DE SÉCURITÉ:
   Layer 1: JWT Token Validation
   Layer 2: Role-Based URL Authorization  
   Layer 3: Data Ownership Verification (Services)

✅ ISOLATION DES DONNÉES:
   • Patient ne voit que SES données
   • Patient ne peut modifier que SON profil
   • Patient NE PEUT modifier autre données

✅ PROTECTION CONTRE:
   • Access tokens invalides/expirés
   • Accès par rôle insuffisant
   • Accès aux données d'autres utilisateurs
   • Tentatives de contournement via API

✅ CONFORMITÉ:
   • OWASP Top 10 (A01: Authentication - Broken Access Control)
   • Principle of Least Privilege
   • Defense in Depth
   • Zero Trust approach
```


