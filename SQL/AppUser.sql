CREATE TABLE AppUser(
id SERIAL PRIMARY KEY NOT NULL,
username VARCHAR(30) NOT NULL,
email VARCHAR(60) NOT NULL,
password VARCHAR(30) NOT NULL
)