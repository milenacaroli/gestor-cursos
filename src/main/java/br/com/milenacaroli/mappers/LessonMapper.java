package br.com.milenacaroli.mappers;

import br.com.milenacaroli.dto.LessonResponse;
import br.com.milenacaroli.model.Lesson;

import java.util.List;

public class LessonMapper {

    public static LessonResponse toResponse(Lesson lesson) {
        return new LessonResponse(lesson.id, lesson.getName());
    }

    public static List<LessonResponse> toResponse(List<Lesson> lessons) {
        return lessons
                .stream()
                .map(lesson -> new LessonResponse(lesson.id, lesson.getName()))
                .toList();
    }
}
