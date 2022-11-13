INSERT INTO ACCOUNT (ACCOUNT_NAME,EMAIL,CREATION_TIME,PASSWORD)
VALUES
('Lider','lider@gmail.com','2022-11-01','$2a$10$eBDxfSIg1nFlAvHlHdI4Gu/lrlRZHICQBA.4iDFHjwCPV.ZGYbRD2'),--Login:lider@gmail.com Hasło:LideroweKontoNaStoProcent
('PodLider','podlider@gmail.com','2022-11-02','$2a$10$N6nuN2whsT6pfg3AYexsReW3/F1DAeuOVHT2pLJy6HseBTFx/uE52'),-- Login:podlider@gmail.com Hasło:PodLideroweKontoNaStoProcent
('Bartolomeo','bartolomeo@gmail.com','2022-11-01','$2a$10$n//D9HCfxn3X9KbXHtxJT.t4AqDq6MRc0PNRiz04w7j5DUqoOAt/u'),-- Login:bartolomeo@gmail.com Hasło:BartolomeoweKontoNaStoProcent
('Admin','admin@gmail.com','2022-10-01','$2a$10$bAKNvM3Amf.d/QMfo46VmuKVwMBRzAtz6vYlBKHCwXgcwedE7b0MG'),-- Login:admin@gmail.com Hasło:BardzoTrudneITajneHasłoAdmina
('Użyszkodnik','uzyszkodnik@gmail.com','2022-11-04','$2a$10$6qg/WMQBGWE3VVJs2XAEh.bQllOJ0dgBDEh.4w4aSpXVQbFRzpqp.');-- Login:uzyszkodnik@gmail.com Hasło:ProsteHasłoUżyszkodnika

INSERT INTO ACCOUNT_ACCOUNT_PERMISSION(PERMISSION_ID,ACCOUNT_ID)
VALUES
(5,1),
(6,1),
(7,1),
(8,1),

(7,2),

(5,4),
(6,4),
(7,4),
(8,4),
(9,4),
(10,4),
(11,4),
(12,4);





