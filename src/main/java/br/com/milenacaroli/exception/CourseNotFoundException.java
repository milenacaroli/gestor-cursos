package br.com.milenacaroli.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Curso não encontrado com ID: " + id);
    }
}
