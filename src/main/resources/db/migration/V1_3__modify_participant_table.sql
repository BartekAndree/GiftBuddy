-- Flyway Script V1_3: Modify Participant Table

-- Allow amount column to be NULL
ALTER TABLE "participant" ALTER COLUMN "amount" DROP NOT NULL;

-- Allow is_anonymous column to be NULL
ALTER TABLE "participant" ALTER COLUMN "is_anonymous" DROP NOT NULL;
