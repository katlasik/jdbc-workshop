package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.model.Teacher;
import org.springframework.jdbc.core.RowMapper;

public class TeacherRowMapper implements RowMapper<Teacher> {

  @Override
  public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
    return new Teacher(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
  }
}
