package sultan.is.instagrammini.dto.request;

public record UserRequest (
         String userName,
         String password,
         String email,
         String phoneNumber
){
}
