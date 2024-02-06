-- Flyway Script V1_0: Initialize Database Schema

-- Create user_profile table
CREATE TABLE IF NOT EXISTS "user_profile" (
    "id" UUID PRIMARY KEY,
    "username" VARCHAR(50) UNIQUE NOT NULL,
    "first_name" VARCHAR(50),
    "last_name" VARCHAR(50),
    "email" VARCHAR(100) UNIQUE NOT NULL,
    "phone_number" VARCHAR(9),
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP
    );

-- Create event table
CREATE TABLE IF NOT EXISTS "event" (
    "id" UUID PRIMARY KEY,
    "organizer_id" UUID NOT NULL REFERENCES "user_profile" ("id") ON DELETE CASCADE,
    "title" VARCHAR(100) NOT NULL,
    "description" TEXT NOT NULL,
    "gift_idea" VARCHAR(100),
    "image_url" VARCHAR(255),
    "current_amount" NUMERIC(8, 2),
    "target_amount" NUMERIC(8, 2) NOT NULL,
    "event_date" DATE NOT NULL,
    "end_date" DATE NOT NULL,
    "is_active" BOOLEAN NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP
    );

-- Create participant table
CREATE TABLE IF NOT EXISTS "participant" (
    "id" UUID PRIMARY KEY,
    "event_id" UUID NOT NULL REFERENCES "event" ("id") ON DELETE CASCADE,
    "user_id" UUID NOT NULL REFERENCES "user_profile" ("id") ON DELETE SET NULL,
    "amount" NUMERIC(8, 2) NOT NULL DEFAULT 0,
    "is_anonymous" BOOLEAN NOT NULL DEFAULT FALSE,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP
    );


-- Add comments for clarity in the schema
COMMENT ON COLUMN "participant"."amount" IS 'The amount contributed by the participant to the event';
COMMENT ON COLUMN "participant"."is_anonymous" IS 'Flag indicating if the contribution is anonymous';
COMMENT ON COLUMN "participant"."updated_at" IS 'Timestamp of the last update of the participant data';
