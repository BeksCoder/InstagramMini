package sultan.is.instagrammini.services;

import sultan.is.instagrammini.dto.request.SignInRequest;
import sultan.is.instagrammini.dto.request.UserRequest;
import sultan.is.instagrammini.dto.response.SignInResponse;
import sultan.is.instagrammini.dto.response.UserResponse;

public interface UserService {
    UserResponse signUp(UserRequest userRequest);

    SignInResponse signIn(SignInRequest signInRequest);

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);



}
