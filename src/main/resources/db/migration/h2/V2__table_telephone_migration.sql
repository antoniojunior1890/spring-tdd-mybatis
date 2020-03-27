CREATE SEQUENCE TELEPHONE_ID_SEQ;

CREATE TABLE TELEPHONE (
    ID          BIGINT DEFAULT TELEPHONE_ID_SEQ.NEXTVAL PRIMARY KEY,
    DDD         VARCHAR(2)    NOT NULL,
    NUMBER      VARCHAR(9)    NOT NULL,
    PERSON_ID   BIGINT SERIAL NOT NULL,
    FOREIGN KEY (PERSON_ID) REFERENCES PERSON(ID)
);