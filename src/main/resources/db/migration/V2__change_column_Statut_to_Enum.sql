
UPDATE render_vous SET statut = 'EN_ATTENTE';
ALTER TABLE render_vous
    MODIFY COLUMN statut
        ENUM('CONFIRME','ANNULE','EN_ATTENTE')
        NOT NULL DEFAULT 'EN_ATTENTE';