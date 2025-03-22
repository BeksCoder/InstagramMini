package sultan.is.instagrammini.exceptions;

public class AccountTypeNotFoundException extends RuntimeException{
    public AccountTypeNotFoundException() {
    }

    public AccountTypeNotFoundException(String message) {
        super(message);
    }
}
