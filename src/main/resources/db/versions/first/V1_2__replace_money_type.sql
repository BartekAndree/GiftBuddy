-- Migracja kolumny "current_amount" w tabeli "event"
ALTER TABLE event
    ALTER COLUMN current_amount TYPE NUMERIC(8, 2);

-- Migracja kolumny "target_amount" w tabeli "event"
ALTER TABLE event
    ALTER COLUMN target_amount TYPE NUMERIC(8, 2);

-- Migracja kolumny "amount" w tabeli "contribution"
ALTER TABLE contribution
    ALTER COLUMN amount TYPE NUMERIC(8, 2);
