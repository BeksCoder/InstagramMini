package sultan.is.instagrammini.dto.request;

public record AuthRequest(
         String usernameOrEmail,
         String password
) {
}
