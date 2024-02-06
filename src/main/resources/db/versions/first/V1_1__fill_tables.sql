-- Insert sample data into user_profile table
INSERT INTO "user_profile" ("id", "username", "first_name", "last_name", "email", "created_at") VALUES
    ('823a23cd-f509-41fc-84ae-9074e6b1849f', 'BartekBartkowicz', 'Bartek', 'Bartkowicz',  'bartekbartkowicz@example.com', NOW()),
    ('ad5fc25b-5b08-4804-92b3-9a730d1d49e1', 'WojciechWojtkowicz', 'Wojciech', 'Wojtkowicz',  'wojciechwojtkowicz@example.com', NOW()),
    ('040bb833-a6d7-435e-b55d-26a8c0554273', 'MarcinMarcinkowski', 'Marcin', 'Marcinkowski',  'marcinmarcinkowski@example.com', NOW()),
    ('2ddd0be0-f24c-4375-b314-131dcb864e56', 'PiotrPiotrowski', 'Piotr', 'Piotrowski',  'piotrpiotrowski@example.com', NOW()),
    ('6fdcd71b-5d8f-48c4-851e-19b64ec41b15', 'JozinZbazin', 'Jozin', 'Zbazin',  'jozinzbazin@example.com', NOW()),
    ('e64cb0c1-acf8-4c0b-b4d5-a9de0a151c7d', 'MlodyG', 'Tytus', 'Szyluk',  'mlodyg@example.com', NOW()),
    ('d50abc42-a5ed-4001-83a7-ad5648105f56', 'PejaSlumsAttac', 'Ryszard ', 'Andrzejewski ',  'pejaslumsattac@example.com', NOW());

-- Insert sample data into event table
INSERT INTO "event" ("id", "organizer_id", "title", "description", "gift_idea", "target_amount", "event_date", "end_date", "is_active", "created_at") VALUES
    ('a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb', '823a23cd-f509-41fc-84ae-9074e6b1849f', 'Birthday Party', 'Celebrating my 30th birthday', 'Gift Card', 300.00, '2024-05-20', '2024-05-10', true, NOW()),
    ('d80808ea-fa10-4d16-b41d-e21179274a54', 'ad5fc25b-5b08-4804-92b3-9a730d1d49e1', 'Wedding', 'Join us to celebrate our special day', 'Cash', 1000.00, '2024-06-15', '2024-06-01', true, NOW()),
    ('eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', '040bb833-a6d7-435e-b55d-26a8c0554273', 'Graduation Party', 'Celebrating my graduation from university', 'Laptop', 1500.00, '2024-07-10', '2024-07-01', true, NOW()),
    ('16d08ff6-b42c-4062-b131-18ccff381d62', '2ddd0be0-f24c-4375-b314-131dcb864e56', 'Baby Shower', 'Celebrating the upcoming arrival of our baby', 'Stroller', 500.00, '2024-08-15', '2024-08-01', true, NOW());

-- Insert sample data into participant table
INSERT INTO "participant" ("id", "event_id", "user_id", "created_at") VALUES
    ('197b5852-64a6-4b15-88bb-6152a1051077', 'a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb', 'ad5fc25b-5b08-4804-92b3-9a730d1d49e1', NOW()),
    ('ae5bf7e6-fa17-43ba-a1ce-3e9b089833f6', 'a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb', '040bb833-a6d7-435e-b55d-26a8c0554273', NOW()),
    ('63220774-8330-422c-86fb-cd655073dc3a', 'd80808ea-fa10-4d16-b41d-e21179274a54', '823a23cd-f509-41fc-84ae-9074e6b1849f', NOW()),
    ('60fdcf2d-ca93-4197-a238-701f3a8e6719', 'd80808ea-fa10-4d16-b41d-e21179274a54', '040bb833-a6d7-435e-b55d-26a8c0554273', NOW()),
    ('f44e3866-78dd-464c-a2c5-99bbe0a0a4cc', 'eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', '823a23cd-f509-41fc-84ae-9074e6b1849f', NOW()),
    ('55305256-bf2a-4230-9c4c-3b1cd3825bd4', 'eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', 'ad5fc25b-5b08-4804-92b3-9a730d1d49e1', NOW()),
    ('5936011d-fa2f-49e4-9cd8-8f73008bbf9c', 'eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', '2ddd0be0-f24c-4375-b314-131dcb864e56', NOW()),
    ('71c542c0-318c-4b03-833b-1d79152ecc95', 'eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', '6fdcd71b-5d8f-48c4-851e-19b64ec41b15', NOW()),
    ('dc52fd4f-d9ca-499f-80d3-89c6ee7d874c', '16d08ff6-b42c-4062-b131-18ccff381d62', '823a23cd-f509-41fc-84ae-9074e6b1849f', NOW()),
    ('02812e12-1be0-4fb8-9e9a-e50e13afcca2', '16d08ff6-b42c-4062-b131-18ccff381d62', 'e64cb0c1-acf8-4c0b-b4d5-a9de0a151c7d', NOW()),
    ('e6f8fcb8-705d-44b6-85fa-2618ec6f858c', '16d08ff6-b42c-4062-b131-18ccff381d62', 'd50abc42-a5ed-4001-83a7-ad5648105f56', NOW());

-- Insert sample data into contribution table
INSERT INTO "contribution" ("id", "event_id", "participant_id", "amount", "is_anonymous", "created_at") VALUES
    ('4813aafc-da53-4d00-9698-01f5cb01b955','a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb', '197b5852-64a6-4b15-88bb-6152a1051077', 50.00, false, NOW()),
    ('c717c015-74a2-41f2-8720-9b6e61544a2c','a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb', 'ae5bf7e6-fa17-43ba-a1ce-3e9b089833f6', 100.00, true, NOW()),
    ('38342ffb-ba3d-4d32-b2c9-89c516934713','d80808ea-fa10-4d16-b41d-e21179274a54', '63220774-8330-422c-86fb-cd655073dc3a', 150.00, false, NOW()),
    ('df1cbd51-a52f-4fd1-9096-80ceef10ac90','d80808ea-fa10-4d16-b41d-e21179274a54', '60fdcf2d-ca93-4197-a238-701f3a8e6719', 200.00, false, NOW()),
    ('172768f0-a33e-4bcf-8b5e-b5db2df30d92','eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', 'f44e3866-78dd-464c-a2c5-99bbe0a0a4cc', 200.00, false, NOW()),
    ('4477781d-65b2-443d-8040-fd8631e8c8c2','eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', '55305256-bf2a-4230-9c4c-3b1cd3825bd4', 250.00, true, NOW()),
    ('0e83cfc5-fdf7-4c1f-a934-31b6246e32b7','eaa64bc3-fdaa-4ab2-86b5-402cd43e77bf', '5936011d-fa2f-49e4-9cd8-8f73008bbf9c', 150.00, false, NOW());
