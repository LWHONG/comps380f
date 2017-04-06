DROP TABLE thread_attachments;
DROP TABLE reply_attachments;
DROP TABLE replies;
DROP TABLE threads;

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