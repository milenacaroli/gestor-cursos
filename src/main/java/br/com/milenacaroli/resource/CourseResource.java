package br.com.milenacaroli.resource;

import br.com.milenacaroli.dto.CourseRequest;
import br.com.milenacaroli.dto.CourseResponse;
import br.com.milenacaroli.dto.LessonRequest;
import br.com.milenacaroli.dto.LessonResponse;
import br.com.milenacaroli.service.CourseService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/courses")
public class CourseResource {

    @Inject //resourse depende do service
    private CourseService courseService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCourse(@Valid CourseRequest request) {

        CourseResponse response = courseService.create(request);
        URI location = URI.create("/courses/" + response.id());

        return Response.created(location)
                .header("Content-Type", "application/json")
                .entity(response)
                .build();

    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCourse(@PathParam("id") Long id, @Valid CourseRequest request) {

        return Response.ok(courseService.update(id, request)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCourse(@PathParam("id") Long id) {
        courseService.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response getCourses(@QueryParam("page") @DefaultValue("1") int page,
                               @QueryParam("size") @DefaultValue("1") int size) {
        List<CourseResponse> courses = courseService.listAll(page, size);

        return Response.ok(courses).build();
    }

    @GET
    @Path("/{id}")
    public Response getCourseById(@PathParam("id") Long id) {

        return Response.ok(courseService.findById(id)).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/lessons")
    @Transactional
    public Response createLesson(@PathParam("id") Long id, @Valid LessonRequest request) {
        LessonResponse response = courseService.addLesson(id, request);

        URI location = URI.create("/courses/" + id + "/lessons/" + response.id());

        return Response.created(location)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}/lessons")
    public Response getLessonsByCourseId(@PathParam("id") Long id) {

        List<LessonResponse> response = courseService.listLessons(id);
        return Response.ok(response).build();
    }
}
