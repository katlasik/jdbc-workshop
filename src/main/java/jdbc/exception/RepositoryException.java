package jdbc.exception;

public class RepositoryException extends RuntimeException {

  public RepositoryException(String message) {
    super(message);
  }

  public RepositoryException(Throwable cause) {
    super(cause);
  }
}
