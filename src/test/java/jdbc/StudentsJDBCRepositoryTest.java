package jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import base.DatabaseSetup;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jdbc.exception.RepositoryException;
import jdbc.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class StudentsJDBCRepositoryTest {

  @RegisterExtension static DatabaseSetup db = new DatabaseSetup();

  private final StudentsJDBCRepository studentsJDBCRepository =
      new StudentsJDBCRepository(db.getDatasource());

  @Test
  @DisplayName("All student entries should be returned")
  void testFindAllStudents() {
    List<Student> result = studentsJDBCRepository.findAllStudents();
    assertThat(result)
        .containsExactlyInAnyOrder(
            new Student(1L, "Szymon", "Kowalski", LocalDate.parse("1999-02-03")),
            new Student(2L, "Krystian", "Nowak", LocalDate.parse("1999-02-03")),
            new Student(3L, "Krystyna", "Kowal", LocalDate.parse("1996-03-11")),
            new Student(4L, "Błażej", "Rudnicki", LocalDate.parse("1998-12-03")));
  }

  @Test
  @DisplayName("All student entries should be counted")
  void testCountStudents() {
    int result = studentsJDBCRepository.countStudents();
    assertThat(result).isEqualTo(4);
  }

  @Test
  @DisplayName("When correct id is passed, student entry should be returned")
  void testFindStudentById() {
    Optional<Student> result = studentsJDBCRepository.findStudentById(1L);
    assertThat(result)
        .contains(new Student(1L, "Szymon", "Kowalski", LocalDate.parse("1999-02-03")));
  }

  @Test
  @DisplayName("When incorrect id is passed, no student entry should be returned")
  void testFindStudentByIdWitIncorrectId() {
    Optional<Student> result = studentsJDBCRepository.findStudentById(10L);
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("When correct student object is passed, then entry should be created")
  void testCreateStudent() {
    Student result =
        studentsJDBCRepository.createStudent(
            new Student(null, "Henryk", "Nowakowski", LocalDate.parse("2000-02-03")));
    Optional<Student> persisted = studentsJDBCRepository.findStudentById(result.getId());
    assertThat(persisted).contains(result);
  }

  @Test
  @DisplayName("When correct student object is passed, then entry should be updated")
  void testUpdateStudent() {
    Student result =
        studentsJDBCRepository.updateStudent(
            new Student(4L, "Szymon", "Urbański", LocalDate.parse("1999-02-03")));
    Optional<Student> persisted = studentsJDBCRepository.findStudentById(4L);
    assertThat(persisted).contains(result);
  }

  @Test
  @DisplayName("When student object with incorrect id is passed, then exception should be thrown")
  void testUpdateStudentWithWrongId() {
    RepositoryException thrown =
        assertThrows(
            RepositoryException.class,
            () ->
                studentsJDBCRepository.updateStudent(
                    new Student(10L, "Szymon", "Urbański", LocalDate.parse("1999-02-03"))));

    assertThat(thrown.getMessage())
        .isEqualTo("Couldn't find student with id=10 to perform update.");
  }

  @Test
  @DisplayName("When correct id is passed, entry should be deleted")
  void testDeleteStudent() {
    studentsJDBCRepository.deleteStudent(4L);
    assertThat(studentsJDBCRepository.findStudentById(4L)).isEmpty();
  }

  @Test
  @DisplayName("When incorrect id is passed, then exception should be thrown")
  void testDeleteStudentWithWrongId() {
    RepositoryException thrown =
        assertThrows(RepositoryException.class, () -> studentsJDBCRepository.deleteStudent(10L));

    assertThat(thrown.getMessage())
        .isEqualTo("Couldn't find student with id=10 to perform delete.");
  }

  @Test
  @DisplayName("When correct name is passed, correct result should be returned")
  void testFindStudentByName() {
    List<Student> startingWithKry = studentsJDBCRepository.findStudentsByName("Kry");

    assertThat(startingWithKry)
        .containsExactlyInAnyOrder(
            new Student(2L, "Krystian", "Nowak", LocalDate.parse("1999-02-03")),
            new Student(3L, "Krystyna", "Kowal", LocalDate.parse("1996-03-11")));

    List<Student> startingWithKow = studentsJDBCRepository.findStudentsByName("Kow");

    assertThat(startingWithKow)
        .containsExactlyInAnyOrder(
            new Student(1L, "Szymon", "Kowalski", LocalDate.parse("1999-02-03")),
            new Student(3L, "Krystyna", "Kowal", LocalDate.parse("1996-03-11")));
  }

  @Test
  @DisplayName("When correct teacher id is passed, correct result should be returned")
  void testFindStudentsByTeacherId() {
    List<Student> students = studentsJDBCRepository.findStudentsByTeacherId(1L);

    assertThat(students)
        .containsExactlyInAnyOrder(
            new Student(1L, "Szymon", "Kowalski", LocalDate.parse("1999-02-03")));
  }

  @Test
  @DisplayName("Correct average age at passed date should be returned")
  void testGetAverageAge() {
    Date date = Date.valueOf("2018-06-01");

    Optional<Double> age = studentsJDBCRepository.getAverageAge(date);

    assertThat(age).contains(19.75);
  }
}
