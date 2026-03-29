package br.com.milenacaroli.resource;

import br.com.milenacaroli.dto.UserRequest;
import br.com.milenacaroli.dto.UserResponse;
import br.com.milenacaroli.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @PermitAll
    @Transactional
    public Response createUser(@Valid UserRequest request) {

        UserResponse response = userService.create(request);
        URI location = URI.create("/users/" + response.id());

        return Response.created(location)
                .header("Content-Type", "application/json")
                .entity(response)
                .build();

    }
}
