USE school;

DROP PROCEDURE IF EXISTS anomize;

DELIMITER $$

CREATE PROCEDURE anomize(IN student_id INT)
BEGIN

    DECLARE name VARCHAR(100);

    SELECT substr(last_name, 1,1) FROM students WHERE id = student_id INTO name;

    SET name := CONCAT(name,'.');

    UPDATE students SET last_name = name WHERE id = student_id;

END

$$
