CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'TRAINER', 'MEMBER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE memberships (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    membership_type VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE workout_classes (
    id SERIAL PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    trainer_id INT NOT NULL,
    schedule TIMESTAMP NOT NULL,
    FOREIGN KEY (trainer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE class_registrations (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    class_id INT NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (class_id) REFERENCES workout_classes(id),
    UNIQUE (user_id, class_id)
);
