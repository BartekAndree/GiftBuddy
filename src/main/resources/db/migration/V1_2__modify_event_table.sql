-- Flyway Script V1_2: Modify Event Table

-- Allow event_date column to be NULL
ALTER TABLE "event" ALTER COLUMN "event_date" DROP NOT NULL;

-- Allow target_amount column to be NULL
ALTER TABLE "event" ALTER COLUMN "target_amount" DROP NOT NULL;

-- Allow description column to be NULL
ALTER TABLE "event" ALTER COLUMN "description" DROP NOT NULL;

-- Allow is_active column to be NULL
ALTER TABLE "event" ALTER COLUMN "is_active" DROP NOT NULL;

-- Add a new column named contribution
ALTER TABLE "event" ADD COLUMN "contribution" NUMERIC(8, 2);

-- Update the contribution column with the default value of 100
UPDATE "event" SET "contribution" = 100.00;