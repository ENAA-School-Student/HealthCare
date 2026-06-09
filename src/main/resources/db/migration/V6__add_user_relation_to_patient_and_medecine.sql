-- V6__add_user_relation_to_patient_and_medecine.sql

-- Fix user_details.id type to BIGINT (was INT in earlier migration)
ALTER TABLE user_details MODIFY id BIGINT NOT NULL AUTO_INCREMENT;

-- Fix patient table
ALTER TABLE patient DROP COLUMN email;
ALTER TABLE patient MODIFY id BIGINT NOT NULL;
ALTER TABLE patient
    ADD CONSTRAINT fk_patient_user_details
        FOREIGN KEY (id) REFERENCES user_details(id);

-- Fix medecine table
ALTER TABLE medecine DROP COLUMN email;
ALTER TABLE medecine MODIFY id BIGINT NOT NULL;
ALTER TABLE medecine
    ADD CONSTRAINT fk_medecine_user_details
        FOREIGN KEY (id) REFERENCES user_details(id);



CREATE TABLE admin (
                       id BIGINT PRIMARY KEY,
                       CONSTRAINT fk_user_admin
                           FOREIGN KEY (id) REFERENCES  user_details(id)
);