package ist.meic.pa.GenericFunctions.exceptions;

public class GenericFunctionException extends RuntimeException {

  public GenericFunctionException() {
  }

  public GenericFunctionException(String message) {
    super(message);
  }

  public GenericFunctionException(String message, Throwable cause) {
    super(message, cause);
  }
}
