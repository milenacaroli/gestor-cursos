package br.com.milenacaroli.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonRequest(@NotNull @NotBlank String name) {
}
