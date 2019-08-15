package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import jdbc.exception.RepositoryException;
import jdbc.model.Student;

class StudentsJDBCRepository {

  private final DataSource dataSource;

  StudentsJDBCRepository(DataSource datasource) {
    this.dataSource = datasource;
  }

  public List<Student> findAllStudents() {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public int countStudents() {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public Optional<Student> findStudentById(long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public Student createStudent(Student student) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public Student updateStudent(Student student) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public void deleteStudent(long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public List<Student> findStudentsByName(String name) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  public List<Student> findStudentsByTeacherId(long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  Optional<Double> getAverageAge(Date date) {
    throw new UnsupportedOperationException("To be implemented.");
  }
}
