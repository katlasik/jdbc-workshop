package jdbc.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class Student {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate birthdate;

  public Student(Long id, String firstName, String lastName, LocalDate birthdate) {

    Objects.requireNonNull(firstName);
    Objects.requireNonNull(lastName);
    Objects.requireNonNull(birthdate);

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("birthdate=" + birthdate)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return Objects.equals(id, student.id)
        && firstName.equals(student.firstName)
        && lastName.equals(student.lastName)
        && birthdate.equals(student.birthdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, birthdate);
  }
}
