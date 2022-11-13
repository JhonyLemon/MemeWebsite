INSERT INTO COMMENT(POST_ID,ACCOUNT_ID,ANSWER_TO_ID,COMMENT)
VALUES
    (1,1,NULL,'Testowy Komentarz'),
    (1,2,1,'Testowy Komentarz'),
    (1,3,1,'Testowy Komentarz'),
    (1,4,2,'Testowy Komentarz'),
    (1,5,4,'Testowy Komentarz'),

    (2,1,NULL,'Testowy Komentarz'),
    (2,2,NULL,'Testowy Komentarz'),
    (2,3,7,'Testowy Komentarz'),
    (2,5,8,'Testowy Komentarz'),

    (3,1,NULL,'Testowy Komentarz'),
    (3,2,NULL,'Testowy Komentarz'),
    (3,3,NULL,'Testowy Komentarz'),
    (3,5,NULL,'Testowy Komentarz');

INSERT INTO COMMENT_STATISTIC(COMMENT_ID,ACCOUNT_ID,VOTE)
VALUES
    (1,1,TRUE),
    (2,2,FALSE),
    (3,3,TRUE),
    (4,4,TRUE),
    (5,5,TRUE),

    (6,1,TRUE),
    (7,2,FALSE),
    (8,3,TRUE),
    (9,5,FALSE),

    (3,1,TRUE),
    (3,2,FALSE),
    (3,3,TRUE),
    (3,5,FALSE);