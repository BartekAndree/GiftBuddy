-- Flyway Script V1_5: Modify participant table

-- Delete column is_anonymous
ALTER TABLE participant DROP COLUMN IF EXISTS is_anonymous;

-- Delete column amount
ALTER TABLE participant DROP COLUMN IF EXISTS amount;

-- Add new column is_settled
ALTER TABLE participant ADD COLUMN is_settled BOOLEAN DEFAULT FALSE;
