CREATE DATABASE it_ticket_system;
USE it_ticket_system;

CREATE TABLE users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(20)
);

CREATE TABLE tickets(
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    category VARCHAR(100),
    priority VARCHAR(50),
    status VARCHAR(50),
    created_date DATE,
    resolved_date DATE,
    assigned_to VARCHAR(100)
);

INSERT INTO users(username,password,role)
VALUES
('admin','admin123','ADMIN'),
('user1','user123','USER');
