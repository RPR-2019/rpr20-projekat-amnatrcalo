BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"username"	TEXT UNIQUE,
	"password"	TEXT,
	PRIMARY KEY("id")
);
COMMIT;
