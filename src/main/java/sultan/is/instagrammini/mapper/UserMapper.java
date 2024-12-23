package sultan.is.instagrammini.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sultan.is.instagrammini.database.model.User;
import sultan.is.instagrammini.dto.request.UserRequest;
import sultan.is.instagrammini.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    User toEntity(UserRequest userRequest);

    UserResponse toResponse(User user);
}
