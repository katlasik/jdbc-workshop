package jdbc;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

import base.DatabaseSetup;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import jdbc.model.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class TeachersJDBCRepositoryTest {

  @RegisterExtension static DatabaseSetup db = new DatabaseSetup();

  private TeachersJDBCRepository teachersJDBCRepository =
      new TeachersJDBCRepository(db.getDatasource());

  @Test
  @DisplayName("When correct id is passed, correct result should be returned")
  void testFindTeacherById() {
    Optional<Teacher> teacher = teachersJDBCRepository.findTeacherById(1L);

    assertThat(teacher).contains(new Teacher(1L, "Damian", "Lewandowski"));
  }

  @Test
  @DisplayName("When correct name is passed, correct result should be returned")
  void testFindStudentsTeachers() {
    List<Teacher> teachers = teachersJDBCRepository.findAllTeachers();

    assertThat(teachers)
        .containsExactlyInAnyOrder(
            new Teacher(1L, "Damian", "Lewandowski"),
            new Teacher(2L, "Beata", "Woźniak"),
            new Teacher(3L, "Artur", "Wójcik"));
  }

  @Test
  @DisplayName("When correct name is passed, correct result should be returned")
  void testFindTeachersWhere() {
    List<Teacher> startingWithMat = teachersJDBCRepository.findTeachersWhere("mat");

    assertThat(startingWithMat).containsExactlyInAnyOrder(new Teacher(1L, "Damian", "Lewandowski"));

    List<Teacher> startingWithWoj = teachersJDBCRepository.findTeachersWhere("wój");

    assertThat(startingWithWoj).containsExactlyInAnyOrder(new Teacher(3L, "Artur", "Wójcik"));

    List<Teacher> startingWithBea = teachersJDBCRepository.findTeachersWhere("bEA");

    assertThat(startingWithBea).containsExactlyInAnyOrder(new Teacher(2L, "Beata", "Woźniak"));
  }

  @Test
  @DisplayName("When correct object is passed, teacher should be created")
  void testCreateTeacher() {
    Teacher teacher =
        teachersJDBCRepository.createTeacher(new Teacher(null, "Paweł", "Waligórski"));
    assertThat(teachersJDBCRepository.findTeacherById(teacher.getId())).contains(teacher);
  }

  @Test
  @DisplayName("When correct list of teachers is passed, teachers should be created")
  void testBatchCreateTeachers() {

    List<Teacher> teachers =
        IntStream.range(0, 100)
            .mapToObj(i -> new Teacher(null, "Name" + i, "LastName" + i))
            .collect(toList());

    assertThat(teachersJDBCRepository.batchCreateTeachers(teachers)).isEqualTo(100);
    assertThat(teachersJDBCRepository.findAllTeachers().size()).isEqualTo(103);
  }
}
