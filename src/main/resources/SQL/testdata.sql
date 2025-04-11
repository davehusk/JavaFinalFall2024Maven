-- Create admin user
INSERT INTO users (username, email, password, role)
VALUES (
  'admin',
  'admin@gym.com',
  '$2a$10$sN6TVQRVkViwaKECzV8IluN4rUtCJNB5w9Fuh5ShNZlii1vM6DwCu',  -- password: admin123
  'ADMIN'
);

-- Create trainer
INSERT INTO users (username, email, password, role)
VALUES (
  'trainer1',
  'trainer1@gym.com',
  '$2a$10$ldcYxoUyy8BB8i49MyKrRO0i6D6b88k.JqWUlEHYqTWUeAu1CdTnu',  -- password: trainer123
  'TRAINER'
);

-- Create member
INSERT INTO users (username, email, password, role)
VALUES (
  'member1',
  'member1@gym.com',
  '$2a$10$R.8UuyM5SNCBCqugjgIqv.7MHOzA7D5G5XAXOL.hS2WYP/BnTCIEG',  -- password: member123
  'MEMBER'
);

-- Insert workout class linked to real trainer
INSERT INTO workout_classes (class_name, trainer_id, schedule)
VALUES (
  'HIIT Burn',
  (SELECT id FROM users WHERE username = 'trainer1'),
  '2025-04-15 18:00:00'
);

-- Insert membership for member1
INSERT INTO memberships (user_id, membership_type, start_date, end_date)
VALUES (
  (SELECT id FROM users WHERE username = 'member1'),
  'Standard',
  CURRENT_DATE,
  DATEADD(MONTH, 1, CURRENT_DATE)
);

-- Register member1 for HIIT Burn class
INSERT INTO class_registrations (user_id, class_id)
VALUES (
  (SELECT id FROM users WHERE username = 'member1'),
  (SELECT id FROM workout_classes WHERE class_name = 'HIIT Burn')
);
