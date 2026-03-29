package br.com.milenacaroli.service;

import br.com.milenacaroli.dto.UserRequest;
import br.com.milenacaroli.dto.UserResponse;
import br.com.milenacaroli.mappers.UserMapper;
import br.com.milenacaroli.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class UserService {
    public UserResponse create(@Valid UserRequest request) {
        User user = UserMapper.toDomain(request);
        user.persist();//gravando usuário
        return UserMapper.toResponse(user);
    }
}
