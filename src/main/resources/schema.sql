DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK(
    id number not null,
    title varchar2(150) not null,
    author varchar2(150) not null,
    description varchar2(150),
    constraint book_pk primary key (id)
);

DROP SEQUENCE IF EXISTS BOOK_SEQ;
CREATE SEQUENCE "BOOK_SEQ"
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1
NOCACHE
NOCYCLE;