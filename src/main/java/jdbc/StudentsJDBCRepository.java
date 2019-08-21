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
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs =
                     statement.executeQuery("SELECT id, first_name, last_name, birthdate FROM students")) {

            ArrayList<Student> students = new ArrayList<>();
            while (rs.next()) {
                students.add(
                        new Student(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDate("birthdate").toLocalDate()));
            }
            return students;

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj `countStudents` tak by zwracała liczbę studentów.
     */

    public int countStudents() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT count(1) FROM students")) {

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj `findStudentById` tak, by zwracało pusty `Optional` jeżeli student o danym **id** nie zostanie znaleziony.
     */
    public Optional<Student> findStudentById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT id, first_name, last_name, birthdate FROM students WHERE id = ?")) {

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            new Student(
                                    rs.getLong("id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getDate("birthdate").toLocalDate()));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj metodę  `createStudent`, która będzie dodawać wiersz w bazie danych.
     * Pole `Id` przekazanego obiektu zawiera null. Metodę należy zaimplementować w ten sposób żeby uzyskać klucz główny z bazy.
     * Możesz do tego użyć metody `statement.getGeneratedKeys()`. Przekaż `Statement.RETURN_GENERATED_KEYS` jako trzeci parametr w `createStatement`.
     * Możesz wykonać akcje dodania wiersza i uzyskania klucza w obrębie jednej transakcji.
     */
    public Student createStudent(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "INSERT INTO students(first_name, last_name, birthdate) VALUES(?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setDate(3, Date.valueOf(student.getBirthdate()));
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    connection.commit();
                    return new Student(
                            rs.getLong(1), student.getFirstName(), student.getLastName(), student.getBirthdate());
                } else {
                    connection.rollback();
                    throw new RepositoryException("Couldn't fetch generated id for student");
                }
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj metodę `updateStudent`, która uaktulnia wiersz w bazie odpowiadający danemu obiektowi.
     * Sprawdź czy zapytanie rzeczywiście zmieniło jeden wiersz. Jeżeli nie to przerwij transakcję i rzuć wyjątek.
     */
    public Student updateStudent(Student student) {
        Objects.requireNonNull(student.getId());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "UPDATE students SET first_name = ?, last_name = ?, birthdate = ? WHERE id = ?")) {
            connection.setAutoCommit(false);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setDate(3, Date.valueOf(student.getBirthdate()));
            statement.setLong(4, student.getId());
            int changedCount = statement.executeUpdate();

            if (changedCount == 1) {
                connection.commit();
                return student;
            } else {
                connection.rollback();
                throw new RepositoryException(
                        String.format("Couldn't find student with id=%s to perform update.", student.getId()));
            }
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj metodę `deleteStudent`, która usunie studenta uzywając id.
     * Jeżeli zostanie usuniętych więcej studentów niż 1, to wycofaj transakcję.
     */
    public void deleteStudent(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement removeStudentClassesStatement =
                     connection.prepareStatement("DELETE FROM school_class_students WHERE student_id = ?");
             PreparedStatement deleteStudentStatement =
                     connection.prepareStatement("DELETE FROM students WHERE id = ?")) {
            connection.setAutoCommit(false);
            removeStudentClassesStatement.setLong(1, id);
            removeStudentClassesStatement.executeUpdate();
            deleteStudentStatement.setLong(1, id);
            int deletedCount = deleteStudentStatement.executeUpdate();
            if (deletedCount != 1) {
                connection.rollback();
                throw new RepositoryException(
                        String.format("Couldn't find student with id=%s to perform delete.", id));
            } else {
                connection.commit();
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj metodę `findStudentsByName` pozwalającą wyszukać studentów po począku imienia lub nazwiska.
     */
    public List<Student> findStudentsByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT id, first_name, last_name, birthdate FROM students WHERE first_name LIKE ? OR last_name LIKE ?")) {

            statement.setString(1, name + "%");
            statement.setString(2, name + "%");

            try (ResultSet rs = statement.executeQuery()) {
                ArrayList<Student> students = new ArrayList<>();
                while (rs.next()) {
                    students.add(
                            new Student(
                                    rs.getLong("id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getDate("birthdate").toLocalDate()));
                }
                return students;
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj metodę `findStudentsByTeacherId` pozwalającą znaleźć studentów po `id` uczącego ich nauczyciela.
     */
    public List<Student> findStudentsByTeacherId(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT students.id, first_name, last_name, birthdate FROM students "
                                     + "JOIN school_class_students ON school_class_students.student_id = students.id "
                                     + "JOIN school_classes ON school_class_students.school_class_id=school_classes.id "
                                     + "WHERE school_classes.teacher_id = ?")) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                ArrayList<Student> students = new ArrayList<>();
                while (rs.next()) {
                    students.add(
                            new Student(
                                    rs.getLong("id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getDate("birthdate").toLocalDate()));
                }
                return students;
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Zaimplementuj `getAverageAge` zwracającą obliczony średni wiek studentów w momencie daty podanej w parametrze.
     */
    Optional<Double> getAverageAge(Date date) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT AVG(TIMESTAMPDIFF(year,birthdate, ?)) "
                     + "AS avg FROM students");
        ) {

            statement.setDate(1, date);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getDouble(1));
            } else {
                return Optional.empty();
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    /**
     * Stwórz procedurę, która anonimizuje dane wszystkich uczniów zastępująć nazwiska, wten sposób,
     * że pozostawia tylko pierwszą literę nazwiska oraz dodaje po niej kropkę. Metoda powinna przyjąć listę id wierszy,
     * które powinny zostać w ten sposób przetworzone. Wywołaj tą procedurę poprzez JDBC.
     */
    void anomize(long id) {
        try (
                Connection connection = dataSource.getConnection();
                CallableStatement statement = connection.prepareCall("call anomize(?)");
        ) {
            statement.setLong(1, id);
            statement.executeQuery();
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }
}
