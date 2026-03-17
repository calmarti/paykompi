INSERT INTO users (
    id, username, password_hash, email,
    first_name, last_name,
    user_role, user_type, user_status,
    created_at, updated_at
)
VALUES (
           gen_random_uuid(),
           'admin',
           '$2a$10$1vxQhxHrLd5mgmgszbsdd.FQLEfVHnXlT2TKzjEQVZFNk0wQUMZd.',
           'admin@paykompi.local',
           '',
           '',
           'ADMIN',
           'ADMIN',
           'ACTIVE',
           NOW(),
           NOW()
       )
    ON CONFLICT (username) DO NOTHING;