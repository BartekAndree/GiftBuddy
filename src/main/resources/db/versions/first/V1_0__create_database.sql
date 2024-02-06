CREATE TABLE IF NOT EXISTS "user_profile" (
    "id" UUID PRIMARY KEY,
    "username" VARCHAR(50) UNIQUE NOT NULL,
    "first_name" VARCHAR(50),
    "last_name" VARCHAR(50),
    "email" VARCHAR(100) UNIQUE NOT NULL,
    "phone_number" VARCHAR(9),
    "created_at" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "event" (
    "id" UUID PRIMARY KEY,
    "organizer_id" UUID NOT NULL,
    "title" VARCHAR(100) NOT NULL,
    "description" TEXT NOT NULL,
    "gift_idea" VARCHAR(100),
    "image_url" VARCHAR(255),
    "current_amount" MONEY,
    "target_amount" MONEY NOT NULL,
    "event_date" DATE NOT NULL,
    "end_date" DATE NOT NULL,
    "is_active" BOOLEAN NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    CONSTRAINT fk_event_organizer_id FOREIGN KEY ("organizer_id") REFERENCES "user_profile" ("id") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "participant" (
    "id" UUID PRIMARY KEY,
    "event_id" UUID NOT NULL,
    "user_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    CONSTRAINT fk_participant_event_id FOREIGN KEY ("event_id") REFERENCES "event" ("id") ON DELETE CASCADE,
    CONSTRAINT fk_participant_user_id FOREIGN KEY ("user_id") REFERENCES "user_profile" ("id") ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS "contribution" (
    "id" UUID PRIMARY KEY,
    "event_id" UUID NOT NULL,
    "participant_id" UUID NOT NULL,
    "amount" MONEY NOT NULL,
    "is_anonymous" BOOLEAN NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    CONSTRAINT fk_contribution_event_id FOREIGN KEY ("event_id") REFERENCES "event" ("id") ON DELETE CASCADE,
    CONSTRAINT fk_contribution_participant_id FOREIGN KEY ("participant_id") REFERENCES "participant" ("id") ON DELETE CASCADE
);