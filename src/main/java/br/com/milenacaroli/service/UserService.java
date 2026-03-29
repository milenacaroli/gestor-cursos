package br.com.milenacaroli.service;

import br.com.milenacaroli.dto.UserRequest;
import br.com.milenacaroli.dto.UserResponse;
import br.com.milenacaroli.exception.UserAlreadyRegistered;
import br.com.milenacaroli.mappers.UserMapper;
import br.com.milenacaroli.model.User;
import br.com.milenacaroli.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    public UserResponse create(@Valid UserRequest request) {
        User existingUser = userRepository.findByEmail(request.email()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyRegistered(request.email());
        }

        User user = UserMapper.toDomain(request);
        user.persist();//gravando usuário
        return UserMapper.toResponse(user);
    }
}
