package br.com.milenacaroli.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity //vai ser gravado no DB
@Table(name = "courses") //tabela cursos
public class Course extends PanacheEntity { //para criar ID

    @Column(nullable = false, length = 255) //coluna name no DB
    private String name;

    @OneToMany
    private List<Lesson> lessons = new ArrayList<>(); //criando Lesson

    public Course() {
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "O nome do curso é obrigatório") @Size(min = 3, message = "O nome do curso deve ter no mínimo 3 caracteres") String name) {

    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}