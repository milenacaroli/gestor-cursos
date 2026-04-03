package br.com.milenacaroli.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof CourseNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(errorBody(404, ex.getMessage()))
                    .build();
        }

        if (exception instanceof ConstraintViolationException ex) {
            List<String> violations = ex.getConstraintViolations().stream()
                    .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                    .collect(Collectors.toList());
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(Map.of("status", 400, "error", "Validação falhou", "violations", violations))
                    .build();
        }

        if (exception instanceof UserAlreadyRegistered ex) {
            return Response.status(Response.Status.CONFLICT)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(errorBody(409, ex.getMessage()))
                    .build();
        }

        if (exception instanceof InvalidDataException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(errorBody(400, ex.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(errorBody(500, "Erro interno do servidor"))
                .build();
    }

    private Map<String, Object> errorBody(int status, String message) {
        return Map.of("status", status, "error", message);
    }
}
