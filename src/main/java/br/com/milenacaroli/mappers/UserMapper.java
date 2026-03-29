package br.com.milenacaroli.mappers;

import br.com.milenacaroli.dto.UserRequest;
import br.com.milenacaroli.dto.UserResponse;
import br.com.milenacaroli.model.User;

public class UserMapper {
    public static User toDomain(UserRequest request) {
        return new User(request.name(), request.email(), request.password());
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.id, user.getName(), user.getEmail(), user.getPassword());
    }
}
