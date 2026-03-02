package br.com.milenacaroli.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson extends PanacheEntity {

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne //muitas lessons para 1 curse
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Lesson() {
    }

    public Lesson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
