INSERT INTO ACCOUNT_PERMISSION(PERMISSION)
VALUES ('USER_EDIT'),
       ('USER_ADD'),
       ('USER_DELETE'),
       ('USER_READ'),
       ('MODERATOR_EDIT'),
       ('MODERATOR_ADD'),
       ('MODERATOR_READ'),
       ('MODERATOR_DELETE'),
       ('ADMIN_ADD'),
       ('ADMIN_READ'),
       ('ADMIN_EDIT'),
       ('ADMIN_DELETE');

INSERT INTO ACCOUNT_ROLE(ROLE,DEFAULT_ROLE)
VALUES ('ADMIN',FALSE),
       ('MODERATOR',FALSE),
       ('USER',TRUE);

INSERT INTO ACCOUNT_ROLE_PERMISSION(ROLE_ID,PERMISSION_ID)
VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (1,5),
       (1,6),
       (1,7),
       (1,8),
       (1,9),
       (1,10),
       (1,11),
       (1,12),
       (2,1),
       (2,2),
       (2,3),
       (2,4),
       (2,5),
       (2,6),
       (2,7),
       (2,8),
       (3,1),
       (3,2),
       (3,3),
       (3,4);