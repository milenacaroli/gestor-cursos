package br.com.milenacaroli.service;

import br.com.milenacaroli.dto.UserRequest;
import br.com.milenacaroli.dto.UserResponse;
import br.com.milenacaroli.exception.InvalidDataException;
import br.com.milenacaroli.exception.UserAlreadyRegistered;
import br.com.milenacaroli.mappers.UserMapper;
import br.com.milenacaroli.model.User;
import br.com.milenacaroli.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    public UserResponse create(@Valid UserRequest request) {
        User existingUser = userRepository.findByEmail(request.email()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyRegistered(request.email());
        }
        if (request.password() == null || request.password().isEmpty() || request.password().length() < 8) {
            throw new InvalidDataException("A senha deve ter no mínimo 8 caracteres");
        }
        if (request.email() == null || request.email().isEmpty() || !request.email().contains("@")) {
            throw new InvalidDataException("E-mail inválido!");
        }
        if (request.name() == null || request.name().isEmpty()) {
            throw new InvalidDataException("O nome é obrigatório!");
        }

        User user = UserMapper.toDomain(request);
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        user.persist();//gravando usuário
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.listAll();
        return UserMapper.toResponse(users);
    }

    public void delete(@Valid UserRequest request) {
        userRepository.delete(userRepository.findByEmail(request.email()).orElse(null));
    }

    @Transactional
    public void createAdmin() {
        Optional<User> optAdmin = userRepository.findByName("admin");
        if (optAdmin.isEmpty()) {
            User admin = new User();
            admin.setName("admin");
            admin.setPassword(BcryptUtil.bcryptHash("admin"));
            admin.setEmail("admin@admin.com.br");
            admin.setRole("ADMIN");
            userRepository.persist(admin);
            LOG.info("Usuário ADMIN criado com sucesso!");
        } else {
            LOG.info("Usuário ADMIN já existe, pulando criação.");
        }
    }
}
