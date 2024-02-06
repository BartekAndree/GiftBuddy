-- Flyway Script V1_7: Alter is_settled column to set default value

ALTER TABLE participant
    ALTER COLUMN is_settled SET DEFAULT false;