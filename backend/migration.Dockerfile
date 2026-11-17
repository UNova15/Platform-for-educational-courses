FROM flyway/flyway:13-alpine

COPY ./user/user-impl/src/main/resources/db/migration /flyway/sql/account

COPY ./course/course-impl/src/main/resources/db/migration /flyway/sql/course

COPY ./progress/progress-impl/src/main/resources/db/migration /flyway/sql/progress

