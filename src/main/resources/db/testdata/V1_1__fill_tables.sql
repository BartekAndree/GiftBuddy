-- Flyway Script V1_1: Insert Sample Data

-- Insert sample data into user_profile table
INSERT INTO "user_profile"
("id",
 "username",
 "first_name",
 "last_name",
 "email",
 "phone_number",
 "created_at",
 "updated_at"
)
VALUES      ( '823a23cd-f509-41fc-84ae-9074e6b1849f',
              'BartekBartkowicz',
              'Bartek',
              'Bartkowicz',
              'bartekbartkowicz@example.com',
              '123456789',
              Now(),
              Now()
            ),
            ( 'ad5fc25b-5b08-4804-92b3-9a730d1d49e1',
              'WojciechWojtkowicz',
              'Wojciech',
              'Wojtkowicz',
              'wojciechwojtkowicz@example.com',
              '987654321',
              Now(),
              Now()
            ),
            ( '040bb833-a6d7-435e-b55d-26a8c0554273',
              'MarcinMarcinkowski',
              'Marcin',
              'Marcinkowski',
              'marcinmarcinkowski@example.com',
              '111222333',
              Now(),
              Now()
            ),
            ( '2ddd0be0-f24c-4375-b314-131dcb864e56',
              'PiotrPiotrowski',
              'Piotr',
              'Piotrowski',
              'piotrpiotrowski@example.com',
              '444555666',
              Now(),
              Now()
            );

-- Insert sample data into event table
INSERT INTO "event"
("id",
 "organizer_id",
 "title",
 "description",
 "gift_idea",
 "target_amount",
 "event_date",
 "end_date",
 "is_active",
 "created_at",
 "updated_at"
)
VALUES      ( 'a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb',
              '823a23cd-f509-41fc-84ae-9074e6b1849f',
              'Birthday Party',
              'Celebrating my 30th birthday',
              'Gift Card',
              300.00,
              '2024-05-20',
              '2024-05-10',
              true,
              Now(),
              Now()
            ),
            ( 'd80808ea-fa10-4d16-b41d-e21179274a54',
              'ad5fc25b-5b08-4804-92b3-9a730d1d49e1',
              'Wedding',
              'Join us to celebrate our special day',
              'Cash',
              1000.00,
              '2024-06-15',
              '2024-06-01',
              true,
              Now(),
              Now()
            );

-- Insert sample data into participant table
INSERT INTO "participant"
("id",
 "event_id",
 "user_id",
 "amount",
 "is_anonymous",
 "created_at",
 "updated_at"
)
VALUES      ( '197b5852-64a6-4b15-88bb-6152a1051077',
              'a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb',
              'ad5fc25b-5b08-4804-92b3-9a730d1d49e1',
              50.00,
              false,
              Now(),
              Now()
            ),
            ( 'ae5bf7e6-fa17-43ba-a1ce-3e9b089833f6',
              'a0fcdda1-e3a9-4ebe-aca8-1ed660b6a4fb',
              '040bb833-a6d7-435e-b55d-26a8c0554273',
              100.00,
              true,
              Now(),
              Now()
            ),
            ( '63220774-8330-422c-86fb-cd655073dc3a',
              'd80808ea-fa10-4d16-b41d-e21179274a54',
              '823a23cd-f509-41fc-84ae-9074e6b1849f',
              150.00,
              false,
              Now(),
              Now()
            ),
            ( '60fdcf2d-ca93-4197-a238-701f3a8e6719',
              'd80808ea-fa10-4d16-b41d-e21179274a54',
              '040bb833-a6d7-435e-b55d-26a8c0554273',
              200.00,
              false,
              Now(),
              Now()
            );