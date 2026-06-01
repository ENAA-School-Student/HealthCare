
ALTER TABLE patient
ADD COLUMN user_id INT,
ADD CONSTRAINT fk_patient_user FOREIGN KEY (user_id) REFERENCES user_details(id);

ALTER TABLE medecine
ADD COLUMN user_id INT,
ADD CONSTRAINT fk_medecine_user FOREIGN KEY (user_id) REFERENCES user_details(id);

