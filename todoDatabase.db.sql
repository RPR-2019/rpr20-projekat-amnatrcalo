BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "quotes" (
	"id"	INTEGER,
	"content"	TEXT,
	"author"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "lists" (
	"username"	TEXT,
	"list_name"	TEXT
);
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"username"	TEXT UNIQUE,
	"mail"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "tasks" (
	"id"	INTEGER,
	"username"	TEXT,
	"task_name"	TEXT,
	"start_year"	INTEGER,
	"start_month"	INTEGER,
	"start_day"	INTEGER,
	"start_hour"	INTEGER,
	"start_min"	INTEGER,
	"end_year"	INTEGER,
	"end_month"	INTEGER,
	"end_day"	INTEGER,
	"end_hour"	INTEGER,
	"end_min"	INTEGER,
	"note"	TEXT,
	"reminder"	INTEGER,
	"reminder_digit"	INTEGER,
	"reminder_period"	TEXT,
	"alert_notification"	INTEGER,
	"alert_email"	INTEGER,
	"list_name"	TEXT,
	"all_day"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "quotes" VALUES (1,'A positive attitude causes a chain reaction of positive thoughts, events, and outcomes.','Wade Boggs');
INSERT INTO "quotes" VALUES (2,'Every day that I wake up has to be a good day!','Bret Michaels');
INSERT INTO "quotes" VALUES (3,'On a good day, when you have a clear plan, you are able to execute whatever you wanted.','Jasprit Bumrah');
INSERT INTO "quotes" VALUES (4,'Anyone can have a good day, but you have to be able to perform on a bad day.','Jurgen Klopp');
INSERT INTO "quotes" VALUES (5,'We don''t see things as they are, we see them as we are.','Anaïs Nin');
INSERT INTO "quotes" VALUES (6,'Where you are is a result of who you were, but where you go depends entirely on who you choose to be.','Hal Elrod');
INSERT INTO "quotes" VALUES (7,'Failure is success if we learn from it.','Malcolm Forbes');
INSERT INTO "quotes" VALUES (8,'Dude, suckin'' at something is the first step to being sorta good at something.','Jake, "Adventure Time"');
INSERT INTO "quotes" VALUES (9,'To reach your greatest potential you''ll have to fight your greatest fears.','Unknown');
INSERT INTO "lists" VALUES ('amna','My Day');
INSERT INTO "lists" VALUES ('amna','Planned');
INSERT INTO "lists" VALUES ('amna','Tasks');
INSERT INTO "lists" VALUES ('amna','Completed');
INSERT INTO "users" VALUES (0,'Amna','Trcalo','amna','amna.trcalo@gmail.com','amna');
COMMIT;
