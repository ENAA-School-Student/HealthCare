CREATE TABLE dossier_medical
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    diagnostic    VARCHAR(255)          NULL,
    observations  VARCHAR(255)          NULL,
    date_creation date                  NULL,
    patient_id    BIGINT                NULL,
    CONSTRAINT pk_dossiermedical PRIMARY KEY (id)
);

CREATE TABLE medecine
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    nom        VARCHAR(255)          NULL,
    telephone  VARCHAR(255)          NULL,
    email      VARCHAR(255)          NULL,
    specialite VARCHAR(255)          NULL,
    CONSTRAINT pk_medecine PRIMARY KEY (id)
);

CREATE TABLE patient
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    nom            VARCHAR(255)          NULL,
    prenom         VARCHAR(255)          NULL,
    email          VARCHAR(255)          NULL,
    telephone      VARCHAR(255)          NULL,
    date_naissance date                  NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE render_vous
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    date_rendez_vous date                  NULL,
    statut           VARCHAR(255)          NULL,
    medecine_id      BIGINT                NULL,
    patient_id       BIGINT                NULL,
    CONSTRAINT pk_rendervous PRIMARY KEY (id)
);