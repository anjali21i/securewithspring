CREATE TABLE user_detail (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enc_password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name ENUM('ADMIN', 'USER', 'MANAGER') UNIQUE NOT NULL,
);

CREATE TABLE user_roles (
    ur_user_id BIGINT NOT NULL,
    ur_role_id BIGINT NOT NULL,
    PRIMARY KEY (ur_user_id, ur_role_id),
    CONSTRAINT fk_user_on_roles FOREIGN KEY (ur_user_id) REFERENCES user_detail(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (ur_role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

INSERT INTO user_detail (user_name, email, enc_password)
 VALUES
('Anjali', 'anjali@gmail.com', '$2a$10$NBRlL1cFTlD69b44n3CLVeP8bnojbgvfAULkdrlAUTrk3SAkUPDa6'),
 ('Aryan', 'aryan@gmail.com', '$2a$10$CthD/ZYHRU7GxFYNDXSG2uBD.JLWUUgLRF4jEs2i0lFusSA7TUBMG');
