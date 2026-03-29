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
import org.jboss.logging.Logger;

import java.time.Duration;

@ApplicationScoped
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class);

    @Inject
    private UserRepository userRepository;

    public SingInResponse login(SignUpRequest signUpRequest) {
        User user = userRepository.findByEmail(signUpRequest.username())
                .orElseThrow(() -> new NotAuthorizedException("Usuário não encontrado!"));

        logger.info("Usuário encontrado: " + user.getEmail());
        logger.info("Senha fornecida: " + signUpRequest.password());
        logger.info("Senha armazenada: " + user.getPassword());

        if (!signUpRequest.password().equals(user.getPassword())) {
            logger.info("Falha na autenticação, senha inválida para o usuário: " + user.getEmail());
            throw new NotAuthorizedException("Usuário ou senha inválidos!");
        }

        try {
            logger.info("Autenticação bem-sucedida para o usuário: " + user.getEmail());
            Duration expiresIn = Duration.ofHours(1);
            String token = Jwt.issuer("http://localhost:8080")
                    .subject(user.getEmail())
                    .groups(user.getRole())
                    .expiresIn(expiresIn)
                    .sign();
            logger.info("Token JWT gerado com sucesso: " + token);
            return new SingInResponse(token, expiresIn.toMillis());
        } catch (Exception e) {
            logger.error("Erro ao gerar token JWT para o usuário: " + user.getEmail(), e);
            throw new RuntimeException("Erro ao gerar token de autenticação! " + e.getMessage(), e);
        }
    }
}
