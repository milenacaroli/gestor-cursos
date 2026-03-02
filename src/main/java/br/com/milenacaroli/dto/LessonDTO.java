package br.com.milenacaroli.dto;

import br.com.milenacaroli.entity.Lesson;

public record LessonDTO(String name) {
    public static LessonDTO fromEntity(Lesson lesson) {
        return new LessonDTO(lesson.getName());
    }
}