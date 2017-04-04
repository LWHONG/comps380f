DROP TABLE user_roles;
DROP TABLE users;

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

--INSERT INTO users VALUES ('hong', 'hongpw');
--INSERT INTO user_roles(username, role) VALUES ('hong', 'ROLE_ADMIN');

--INSERT INTO users VALUES ('erica', 'ericapw');
--INSERT INTO user_roles(username, role) VALUES ('erica', 'ROLE_ADMIN');

--INSERT INTO users VALUES ('timothy', 'timothypw');
--INSERT INTO user_roles(username, role) VALUES ('timothy', 'ROLE_ADMIN');

--INSERT INTO users VALUES ('oliver', 'oliverpw');
--INSERT INTO user_roles(username, role) VALUES ('oliver', 'ROLE_USER');