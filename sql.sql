BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "tb_account" (
	"id"	INTEGER,
	"account"	TEXT NOT NULL,
	"value"	REAL NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "tb_card" (
	"id"	INTEGER,
	"value"	REAL NOT NULL,
	"date"	DATE NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
COMMIT;
CREATE TABLE "tb_history" (
	"id"	INTEGER,
	"data_list"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);