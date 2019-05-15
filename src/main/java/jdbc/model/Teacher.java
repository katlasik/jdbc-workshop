package jdbc.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Teacher {

  private Long id;
  private String firstName;
  private String lastName;

  public Teacher(Long id, String firstName, String lastName) {

    Objects.requireNonNull(firstName);
    Objects.requireNonNull(lastName);

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
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

  @Override
  public String toString() {
    return new StringJoiner(", ", Teacher.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Teacher student = (Teacher) o;
    return Objects.equals(id, student.id)
        && firstName.equals(student.firstName)
        && lastName.equals(student.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName);
  }
}
