DROP TABLE poll_answers;
DROP TABLE polls;

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