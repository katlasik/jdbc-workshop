package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import jdbc.model.Teacher;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TeacherResultSetExtractor implements ResultSetExtractor<Optional<Teacher>> {

  @Override
  public Optional<Teacher> extractData(ResultSet rs) throws SQLException {
    throw new UnsupportedOperationException("To be implemented.");
  }
}
