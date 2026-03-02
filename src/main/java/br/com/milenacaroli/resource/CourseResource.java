package br.com.milenacaroli.resource;

import br.com.milenacaroli.dto.CourseDTO;
import br.com.milenacaroli.dto.LessonDTO;
import br.com.milenacaroli.service.CourseService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/courses")//endereço no navegador
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Courses", description = "Operações de gerenciamento de cursos e aulas")
public class CourseResource {

    @Inject
    private CourseService courseService;

    // Criando cursos

    @POST
    @Operation(summary = "Criar um novo curso") //documentar a api
    public Response create(@Valid CourseDTO request) { //pedido de criação de curso
        CourseDTO created = courseService.create(request);//criando objeto
        URI location = UriBuilder.fromResource(CourseResource.class)//devolve a resposta
                .path(String.valueOf(created.id()))
                .build();
        return Response.created(location).entity(created).build();//cod 201 http
    }

    // Buscar (listar) cursos

    @GET
    @Operation(summary = "Listar todos os cursos")
    public Response listAll() {
        List<CourseDTO> cursos = courseService.listAll();//pede cursos
        return Response.ok(cursos).build();//retorna lista cod 200
    }

    // ── GET /courses/{id} ─────────────────────────────────────────────────────

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar curso por ID")
    public Response findById(@PathParam("id") Long id) {
        CourseDTO.Response course = courseService.findById(id);
        return Response.ok(course).build();
    }

    // ── PUT /courses/{id} ─────────────────────────────────────────────────────

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar curso")
    public Response update(@PathParam("id") Long id, @Valid CourseDTO.Request request) {
        CourseDTO.Response updated = courseService.update(id, request);
        return Response.ok(updated).build();
    }

    // ── DELETE /courses/{id} ──────────────────────────────────────────────────

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remover curso")
    public Response delete(@PathParam("id") Long id) {
        courseService.delete(id);
        return Response.noContent().build();
    }

    // ── POST /courses/{courseId}/lessons ──────────────────────────────────────

    @POST
    @Path("/{courseId}/lessons")
    @Operation(summary = "Adicionar aula a um curso")
    public Response addLesson(@PathParam("courseId") Long courseId,
                              @Valid LessonDTO.Request request) {
        LessonDTO.Response lesson = courseService.addLesson(courseId, request);
        URI location = UriBuilder.fromResource(CourseResource.class)
                .path(String.valueOf(courseId))
                .path("lessons")
                .path(String.valueOf(lesson.id))
                .build();
        return Response.created(location).entity(lesson).build();
    }

    // ── GET /courses/{courseId}/lessons ───────────────────────────────────────

    @GET
    @Path("/{courseId}/lessons")
    @Operation(summary = "Listar todas as aulas de um curso")
    public Response listLessons(@PathParam("courseId") Long courseId) {
        List<LessonDTO.Response> lessons = courseService.listLessons(courseId);
        return Response.ok(lessons).build();
    }
}
