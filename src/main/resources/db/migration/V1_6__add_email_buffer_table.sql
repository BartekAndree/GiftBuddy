-- Flyway Script V1_6: Add email_buffer table

-- Create email_buffer table
CREATE TABLE IF NOT EXISTS "email_buffer" (
    "id" UUID PRIMARY KEY,
    "event_id" UUID NOT NULL,
    "email" VARCHAR(100) NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP
);