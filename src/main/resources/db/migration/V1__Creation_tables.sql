CREATE TABLE user_details
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)          NULL,
    email    VARCHAR(255)          NULL,
    password VARCHAR(255)          NULL,
    role     VARCHAR(255)          NULL,
    CONSTRAINT pk_user_details PRIMARY KEY (id)
);

CREATE TABLE patient
(
    id             BIGINT NOT NULL,
    nom            VARCHAR(255) NULL,
    prenom         VARCHAR(255) NULL,
    telephone      VARCHAR(255) NULL,
    date_naissance date         NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id),
    CONSTRAINT fk_patient_user_details FOREIGN KEY (id) REFERENCES user_details (id)
);

CREATE TABLE medecine
(
    id         BIGINT NOT NULL,
    nom        VARCHAR(255) NULL,
    telephone  VARCHAR(255) NULL,
    specialite VARCHAR(255) NULL,
    CONSTRAINT pk_medecine PRIMARY KEY (id),
    CONSTRAINT fk_medecine_user_details FOREIGN KEY (id) REFERENCES user_details (id)
);

CREATE TABLE admin
(
    id BIGINT NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (id),
    CONSTRAINT fk_admin_user_details FOREIGN KEY (id) REFERENCES user_details (id)
);

CREATE TABLE dossier_medical
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    diagnostic    VARCHAR(255)          NULL,
    observations  VARCHAR(255)          NULL,
    date_creation date                  NULL,
    patient_id    BIGINT                NULL,
    CONSTRAINT pk_dossiermedical PRIMARY KEY (id),
    CONSTRAINT uc_dossiermedical_patient UNIQUE (patient_id),
    CONSTRAINT FK_DOSSIERMEDICAL_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id)
);

CREATE TABLE render_vous
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    date_rendez_vous date                  NULL,
    statut           VARCHAR(255)          NULL,
    medecine_id      BIGINT                NULL,
    patient_id       BIGINT                NULL,
    CONSTRAINT pk_rendervous PRIMARY KEY (id),
    CONSTRAINT FK_RENDERVOUS_ON_MEDECINE FOREIGN KEY (medecine_id) REFERENCES medecine (id),
    CONSTRAINT FK_RENDERVOUS_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id)
);