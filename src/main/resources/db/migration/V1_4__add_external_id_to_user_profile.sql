-- Flyway Script V1_4: Add external_id to user profile table

-- Add a new column named contribution
ALTER TABLE "user_profile" ADD COLUMN "external_id" UUID;
