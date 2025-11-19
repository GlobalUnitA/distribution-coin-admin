INSERT INTO users (
    username,
    password_hash,
    name,
    enabled,
    created_at,
    updated_at
) VALUES (
             'admin2',
             '$2a$10$f95hdMCt7C0xwrvDwguL9e8ZsPrk78p8kbyLFslrJ6eK0AbxIaLEG',
             '관리자2',
             1,
             NOW(),
             NOW()
         );