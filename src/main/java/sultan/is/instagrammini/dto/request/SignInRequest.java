package sultan.is.instagrammini.dto.request;

public record SignInRequest(
         String usernameOrEmail,
         String password
) {
}
