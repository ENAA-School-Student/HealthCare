ALTER TABLE dossier_medical
    ADD CONSTRAINT uc_dossiermedical_patient UNIQUE (patient_id);

ALTER TABLE dossier_medical
    ADD CONSTRAINT FK_DOSSIERMEDICAL_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id);

ALTER TABLE render_vous
    ADD CONSTRAINT FK_RENDERVOUS_ON_MEDECINE FOREIGN KEY (medecine_id) REFERENCES medecine (id);

ALTER TABLE render_vous
    ADD CONSTRAINT FK_RENDERVOUS_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id);