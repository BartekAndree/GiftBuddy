-- Flyway Script V1_8: Alter event current_amount column to default 0

ALTER TABLE event
    ALTER COLUMN current_amount SET DEFAULT 0;