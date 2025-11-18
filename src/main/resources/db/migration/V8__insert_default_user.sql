INSERT INTO users (
    username,
    password_hash,
    name,
    enabled,
    create_at,
    update_at
) VALUES (
 'admin',
 '$2a$10$xF6Ukl1LMXKlUf0wqWvz6.JFGjbYRKwpJnQG5O4T3Am7VfXYHLmzi',
 '관리자',
 1,
 NOW(),
 NOW()
);