package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import jdbc.model.Teacher;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TeacherResultSetExtractor implements ResultSetExtractor<Optional<Teacher>> {

  @Override
  public Optional<Teacher> extractData(ResultSet rs) throws SQLException {
    if (rs.next()) {
      return Optional.of(new Teacher(rs.getLong(1), rs.getString(2), rs.getString(3)));
    } else {
      return Optional.empty();
    }
  }
}
