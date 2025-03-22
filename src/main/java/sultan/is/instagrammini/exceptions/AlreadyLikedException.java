package sultan.is.instagrammini.exceptions;

public class AlreadyLikedException extends RuntimeException{
    public AlreadyLikedException() {
    }

    public AlreadyLikedException(String message) {
        super(message);
    }
}
