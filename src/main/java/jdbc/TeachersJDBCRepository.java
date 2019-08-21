package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import jdbc.model.Teacher;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class TeachersJDBCRepository {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public TeachersJDBCRepository(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  /**
   * Zaimplementuj `findTeacherById` wzracający obiekt `Teacher` po `id.
   */
  public Optional<Teacher> findTeacherById(Long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj `findAllTeacher` zwracający wszystkich nauczycieli.
   */
  public List<Teacher> findAllTeachers() {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `findTeachersWhere` wyszukującą nauczycieli po imienu lub nazwisku lub nazwie prowadzonego przez niego przedmiotu.
   * Wyszukiwanie powinno być *case-insensitive*, czyli wielkość znaków nie powinna mieć znaczenia.
   */
  public List<Teacher> findTeachersWhere(String query) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `createTeacher`, pozwalającą na dodanie nowego nauczyciela.
   */
  public Teacher createTeacher(Teacher teacher) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `batchCreateTeachers`, pozwalającą na zbiorcze dodanie nowych nauczycieli.
   */
  public int batchCreateTeachers(List<Teacher> teachers) {
    throw new UnsupportedOperationException("To be implemented.");
  }
}
