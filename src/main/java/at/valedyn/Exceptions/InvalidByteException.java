package at.valedyn.Exceptions;

public class InvalidByteException extends Exception{
    public InvalidByteException(String message) {
        super(message);
    }

    public InvalidByteException() {
        super();
    }

    public InvalidByteException(String message, Throwable cause) {
        super(message, cause);
    }
}
