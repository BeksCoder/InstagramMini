package sultan.is.instagrammini.services.implementation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sultan.is.instagrammini.database.model.User;
import sultan.is.instagrammini.dto.request.SignInRequest;
import sultan.is.instagrammini.dto.request.UserRequest;
import sultan.is.instagrammini.dto.response.SignInResponse;
import sultan.is.instagrammini.dto.response.UserResponse;
import sultan.is.instagrammini.exceptions.UserAlreadyExistsException;
import sultan.is.instagrammini.exceptions.UserNotFoundException;
import sultan.is.instagrammini.mapper.UserMapper;
import sultan.is.instagrammini.repositories.UserRepository;
import sultan.is.instagrammini.services.UserService;

import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponse signUp(UserRequest userRequest) {
        if (userRepository.existsByUsernameOrEmail(userRequest.email(), userRequest.userName())) {
            log.error(String.format("Пользователь с адресом электронной почты %s уже существует", userRequest.email()));
            throw new UserAlreadyExistsException(String.format("""
                    User with email %s or username %s already exists.
                    Please try a different one.
                    """, userRequest.email(), userRequest.userName()));
        }
        User user = userMapper.toEntity(userRequest);
        User userSaved = userRepository.save(user);
        return userMapper.toResponse(userSaved);
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {

        User user = userRepository.findByUsernameOrEmail(signInRequest.usernameOrEmail(),signInRequest.usernameOrEmail())
                .orElseThrow(()->new UserNotFoundException(
                        String.format("Неверные учетные данные",signInRequest.usernameOrEmail())));
        if(!signInRequest.password().equals(user.getPassword())){

        }
        return null;
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
