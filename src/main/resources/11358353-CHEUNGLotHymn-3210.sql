DROP TABLE user_roles;
DROP TABLE users;
DROP TABLE thread_attachments;
DROP TABLE reply_attachments;
DROP TABLE replies;
DROP TABLE threads;
DROP TABLE poll_answers;
DROP TABLE polls;

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE threads (
    thread_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(500) NOT NULL,
    category VARCHAR(50) NOT NULL,
    PRIMARY KEY (thread_id)
);

CREATE TABLE replies (
    reply_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    content VARCHAR(500) NOT NULL,
    thread_id INTEGER NOT NULL,
    PRIMARY KEY (reply_id),
    FOREIGN KEY (thread_id) REFERENCES threads(thread_id)
);

CREATE TABLE thread_attachments (
    attachment_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(150) NOT NULL,
    filetype VARCHAR(100) NOT NULL,
    filepath VARCHAR(300) NOT NULL,
    thread_id INTEGER NOT NULL,
    PRIMARY KEY (attachment_id),
    FOREIGN KEY (thread_id) REFERENCES threads(thread_id)
);

CREATE TABLE reply_attachments (
    attachment_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(150) NOT NULL,
    filetype VARCHAR(100) NOT NULL,
    filepath VARCHAR(300) NOT NULL,
    reply_id INTEGER NOT NULL,
    PRIMARY KEY (attachment_id),
    FOREIGN KEY (reply_id) REFERENCES replies(reply_id)
);

CREATE TABLE polls (
    poll_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    question VARCHAR(300) NOT NULL,
    option_a VARCHAR(300) NOT NULL,
    option_b VARCHAR(300) NOT NULL,
    option_c VARCHAR(300) NOT NULL,
    option_d VARCHAR(300) NOT NULL,
    create_timestamp timestamp NOT NULL,
    enable boolean NOT NULL DEFAULT false,
    PRIMARY KEY (poll_id)
);

CREATE TABLE poll_answers (
    poll_answer_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    answer VARCHAR(8) NOT NULL,
    create_timestamp timestamp NOT NULL,
    poll_id INTEGER NOT NULL,
    PRIMARY KEY (poll_answer_id),
    FOREIGN KEY (poll_id) REFERENCES polls(poll_id)
);

--INSERT INTO polls (question, option_a, option_b, option_c, option_d, create_timestamp, enable) VALUES ('What kind of IT jobs do you like to do?', 'Programer', 'Web Designer', 'Database Administrator', 'IT Security Officer',  current_timestamp, true);