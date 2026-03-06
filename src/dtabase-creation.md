Login to PostgreSQL

docker exec -it sh psql -U postgres

Create a new database

CREATE DATABASE cricket_db;

Create a new user and grant privileges
CREATE USER cricket_user WITH PASSWORD 'strongpassword';
GRANT ALL PRIVILEGES ON DATABASE cricket_db TO cricket_user;

Connect to the Database

\c cricket_db

Grant schema permissions

GRANT ALL ON SCHEMA public TO cricket_user;
ALTER SCHEMA public OWNER TO cricket_user;

Now you have a cricket_db created with a user cricket_user that has all privileges on the database and the public schema. You can now use this database to store your cricket-related data.

you you can use this user and database in spring boot application properties to connect to the database

