DROP TABLE IF EXISTS class_attendance;
DROP TABLE IF EXISTS workout_classes;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS membership_plans;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  email TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  role TEXT NOT NULL,
  joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE membership_plans (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  price DECIMAL NOT NULL,
  duration_days INTEGER NOT NULL
);

CREATE TABLE memberships (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id),
  plan_id INTEGER REFERENCES membership_plans(id),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL
);

CREATE TABLE workout_classes (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  schedule TEXT,
  trainer_id INTEGER REFERENCES users(id)
);

CREATE TABLE class_attendance (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id),
  class_id INTEGER REFERENCES workout_classes(id),
  attended_at DATE DEFAULT CURRENT_DATE
);
