BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"username"	TEXT UNIQUE,
	"password"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "quotes" (
	"id"	INTEGER,
	"content"	TEXT,
	"author"	TEXT,
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
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "lists" (
	"username"	TEXT,
	"list_name"	TEXT
);
INSERT INTO "users" VALUES (0,'Amna','Trcalo','amna','amna');
INSERT INTO "quotes" VALUES (0,'A positive attitude causes a chain reaction of positive thoughts, events, and outcomes.','Wade Boggs');
INSERT INTO "quotes" VALUES (1,'Every day that I wake up has to be a good day!','Bret Michaels');
INSERT INTO "quotes" VALUES (2,'On a good day, when you have a clear plan, you are able to execute whatever you wanted.','Jasprit Bumrah');
INSERT INTO "quotes" VALUES (3,'Anyone can have a good day, but you have to be able to perform on a bad day.','Jurgen Klopp');
INSERT INTO "quotes" VALUES (4,' Don''t wake up with the regret of what you couldn''t accomplish yesterday. Wake up while thinking about what will be able to achieve today. ','Unknown');
INSERT INTO "quotes" VALUES (5,'We don''t see things as they are, we see them as we are.','Anaïs Nin');
COMMIT;
