CREATE SCHEMA IF NOT EXISTS sqlinjection;

CREATE TABLE BOOK
(
    ID          BIGSERIAL PRIMARY KEY,
    TITLE TEXT NOT NULL,
    CONSTRAINT name_length CHECK (length(TITLE) <= 512)
);