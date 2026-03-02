package br.com.milenacaroli.dto;

import br.com.milenacaroli.entity.Course;

public record CourseDTO(Long id, String name) {
    public static CourseDTO fromEntity(Course course) {
        return new CourseDTO(course.getId(), course.getName());
    }

    public static Course toEntity(CourseDTO courseDTO) {
        return new Course(courseDTO.id(), courseDTO.name());
    }
}