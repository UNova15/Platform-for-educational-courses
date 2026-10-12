CREATE TABLE progress.enrollments
(
    user_id    INT,
    course_id  INT,
    created_at TIMESTAMP,
    PRIMARY KEY (user_id, course_id)
);