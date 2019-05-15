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

  public Optional<Teacher> findTeacherById(Long id) {
    return jdbcTemplate.query(
        "SELECT id, first_name, last_name FROM teachers WHERE id = ?",
        new Object[] {id},
        new TeacherResultSetExtractor());
  }

  public List<Teacher> findAllTeachers() {
    return jdbcTemplate.query(
        "SELECT id, first_name, last_name FROM teachers", new TeacherRowMapper());
  }

  public List<Teacher> findTeachersWhere(String query) {
    return namedParameterJdbcTemplate.query(
        "SELECT teachers.id, first_name, last_name FROM teachers "
            + "JOIN school_classes ON school_classes.teacher_id = teachers.id "
            + "WHERE lower(last_name) LIKE lower(:query) "
            + "OR lower(first_name) LIKE lower(:query) "
            + "OR lower(school_classes.name) LIKE lower(:query) ",
        new MapSqlParameterSource("query", "%" + query + "%"),
        new TeacherRowMapper());
  }

  public Teacher createTeacher(Teacher teacher) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(
        "INSERT INTO teachers(first_name, last_name) VALUES(:firstName, :lastName)",
        new MapSqlParameterSource("firstName", teacher.getFirstName())
            .addValue("lastName", teacher.getLastName()),
        keyHolder);

    return new Teacher(
        keyHolder.getKey().longValue(), teacher.getFirstName(), teacher.getLastName());
  }

  public int batchCreateTeachers(List<Teacher> teachers) {

    int[] modified =
        jdbcTemplate.batchUpdate(
            "INSERT INTO teachers(first_name, last_name) VALUES(?, ?)",
            new BatchPreparedStatementSetter() {
              public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, teachers.get(i).getFirstName());
                ps.setString(2, teachers.get(i).getLastName());
              }

              public int getBatchSize() {
                return teachers.size();
              }
            });

    return Arrays.stream(modified).sum();
  }
}
