DROP DATABASE IF EXISTS school;
DROP USER IF EXISTS school_user;

USE mysql;
CREATE DATABASE school DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
CREATE USER 'school_user'@'%' IDENTIFIED BY 'pass';
GRANT ALL ON school.* TO 'school_user'@'%';
GRANT SUPER ON *.* TO 'school_user'@'%';
FLUSH PRIVILEGES;

CREATE TABLE school.students (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    first_name CHAR(100) NOT NULL,
    last_name CHAR(100) NOT NULL,
    birthdate DATE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE school.teachers (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    first_name CHAR(100) NOT NULL,
    last_name CHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE school.school_classes (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    name CHAR(200) NOT NULL,
    teacher_id MEDIUMINT NOT NULL REFERENCES school.teachers(id),
    PRIMARY KEY(id)
);

CREATE TABLE school.school_class_students (
    school_class_id MEDIUMINT NOT NULL,
    student_id MEDIUMINT NOT NULL REFERENCES school.students(id),
    PRIMARY KEY(school_class_id, student_id),
    FOREIGN KEY (school_class_id) REFERENCES school.school_classes(id) ON DELETE RESTRICT,
    FOREIGN KEY (student_id) REFERENCES school.students(id) ON DELETE RESTRICT
);

SOURCE "anomize.sql";

