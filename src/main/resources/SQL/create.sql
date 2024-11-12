CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cars (
                      car_id SERIAL PRIMARY KEY,
                      make VARCHAR(50) NOT NULL,
                      model VARCHAR(50) NOT NULL,
                      year INT NOT NULL,
                      price DECIMAL(10, 2) NOT NULL,
                      seller_id INT NOT NULL,
                      FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE
);
