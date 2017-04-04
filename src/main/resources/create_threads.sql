DROP TABLE replies;
DROP TABLE threads;

CREATE TABLE threads (
    thread_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
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