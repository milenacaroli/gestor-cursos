package br.com.milenacaroli.mappers;

import br.com.milenacaroli.dto.CourseRequest;
import br.com.milenacaroli.dto.CourseResponse;
import br.com.milenacaroli.model.Course;

public class CourseMapper {
    public static Course toDomain(CourseRequest courseRequest) {
        return new Course(courseRequest.name());
    }

    public static CourseResponse toResponse(Course course) {
        return new CourseResponse(course.id, course.getName(), LessonMapper.toResponse(course.getLessons()));
    }
}
