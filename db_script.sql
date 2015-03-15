CREATE TABLE users (
  code      VARCHAR PRIMARY KEY NOT NULL,
  username  TEXT                NOT NULL,
  firstname TEXT,
  lastname  TEXT,
  age       INTEGER,
  birth     DATE,
  country   TEXT,
  city      TEXT,
  street    TEXT,
  flag      TEXT
);
