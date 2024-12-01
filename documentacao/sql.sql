CREATE TABLE "tb_user" (
	"id"	INTEGER,
	"user"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	"user_code"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);