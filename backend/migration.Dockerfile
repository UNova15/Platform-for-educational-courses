FROM flyway/flyway:13-alpine

COPY ./auth/auth-impl/src/main/resources/db/migration /flyway/sql/auth

COPY ./course/course-impl/src/main/resources/db/migration /flyway/sql/course

COPY ./progress/progress-impl/src/main/resources/db/migration /flyway/sql/progress

