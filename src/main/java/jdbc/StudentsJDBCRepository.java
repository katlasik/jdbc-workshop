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

  /**
   * Zaimplementuj `findAllStudents` tak by zwracała listę wszystkich studentów.
   */
  public List<Student> findAllStudents() {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj `countStudents` tak by zwracała liczbę studentów.
   */
  public int countStudents() {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj `findStudentById` tak, by zwracało pusty `Optional` jeżeli student o danym **id** nie zostanie znaleziony.
   */
  public Optional<Student> findStudentById(long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę  `createStudent`, która będzie dodawać wiersz w bazie danych.
   * Pole `Id` przekazanego obiektu zawiera null. Metodę należy zaimplementować w ten sposób żeby uzyskać klucz główny z bazy.
   * Możesz do tego użyć metody `statement.getGeneratedKeys()`. Przekaż `Statement.RETURN_GENERATED_KEYS` jako trzeci parametr w `createStatement`.
   * Możesz wykonać akcje dodania wiersza i uzyskania klucza w obrębie jednej transakcji.
   */
  public Student createStudent(Student student) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `updateStudent`, która uaktulnia wiersz w bazie odpowiadający danemu obiektowi.
   * Sprawdź czy zapytanie rzeczywiście zmieniło jeden wiersz. Jeżeli nie to przerwij transakcję i rzuć wyjątek.
   */
  public Student updateStudent(Student student) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `deleteStudent`, która usunie studenta uzywając id.
   * Jeżeli zostanie usuniętych więcej studentów niż 1, to wycofaj transakcję.
   */
  public void deleteStudent(long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `findStudentsByName` pozwalającą wyszukać studentów po począku imienia lub nazwiska.
   */
  public List<Student> findStudentsByName(String name) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj metodę `findStudentsByTeacherId` pozwalającą znaleźć studentów po `id` uczącego ich nauczyciela.
   */
  public List<Student> findStudentsByTeacherId(long id) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Zaimplementuj `getAverageAge` zwracającą obliczony średni wiek studentów.
   */
  Optional<Double> getAverageAge(Date date) {
    throw new UnsupportedOperationException("To be implemented.");
  }

  /**
   * Stwórz procedurę, która anonimizuje dane wszystkich uczniów zastępująć nazwiska, wten sposób,
   * że pozostawia tylko pierwszą literę nazwiska oraz dodaje po niej kropkę. Metoda powinna przyjąć listę id wierszy,
   * które powinny zostać w ten sposób przetworzone. Wywołaj tą procedurę poprzez JDBC.
   */
  void anomize(long id) {

  }
}
