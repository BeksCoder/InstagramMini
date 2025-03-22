package sultan.is.instagrammini.services;

import sultan.is.instagrammini.database.model.User;

public interface AuthService {

    User registerUser(String username, String password, String email);

    String authenticateUser(String username, String password);

    void logoutUser(String token);

    void changePassword(String username, String oldPassword, String newPassword);

    boolean resetPassword(String email);

    boolean isAuthenticated(String token);

    boolean userExists(String username);

    boolean isPasswordSecure(String password);
}


