package ch6.properties;

public class MissingPropertiesException extends Exception {

  private static final long serialVersionUID = 1L;

  public MissingPropertiesException(final String string) {
    super(string);
  }
}
