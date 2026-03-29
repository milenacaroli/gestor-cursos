package br.com.milenacaroli.service;

import br.com.milenacaroli.dto.SignUpRequest;
import br.com.milenacaroli.dto.SingInResponse;
import br.com.milenacaroli.model.User;
import br.com.milenacaroli.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

import java.time.Duration;

@ApplicationScoped
public class AuthService {
    @Inject
    private UserRepository userRepository;

    public SingInResponse login(SignUpRequest signUpRequest) {
        User user = userRepository.findByEmail(signUpRequest.username())
                .orElseThrow(() -> new NotAuthorizedException("Usuário não encontrado!"));

        if (!BcryptUtil.matches(user.getPassword(), signUpRequest.password())) {
            throw new NotAuthorizedException("Usuário ou senha inválidos!");
        }

        Duration expiresIn = Duration.ofHours(1);
        String token = Jwt.issuer("http://localhost:8080")
                .subject(user.getEmail())
                .groups(user.getRole())
                .expiresIn(expiresIn)
                .sign();
        return new SingInResponse(token, expiresIn.toMillis());
    }
}
