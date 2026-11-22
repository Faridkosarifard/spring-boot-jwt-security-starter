INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_MODERATOR'), ('ROLE_ADMIN');

INSERT INTO permissions (name) VALUES
    ('user:read'),
    ('user:write'),
    ('admin:read'),
    ('admin:write');

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON
    (r.name = 'ROLE_USER' AND p.name IN ('user:read', 'user:write'))
    OR (r.name = 'ROLE_MODERATOR' AND p.name IN ('user:read', 'user:write', 'admin:read'))
    OR (r.name = 'ROLE_ADMIN' AND p.name IN ('user:read', 'user:write', 'admin:read', 'admin:write'));
