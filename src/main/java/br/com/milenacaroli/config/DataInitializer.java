package br.com.milenacaroli.config;

import br.com.milenacaroli.service.UserService;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@Startup
@ApplicationScoped
public class DataInitializer {

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        userService.createAdmin();
    }
}
