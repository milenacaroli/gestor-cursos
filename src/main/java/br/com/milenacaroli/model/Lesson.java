package br.com.milenacaroli.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lessons")
public class Lesson extends PanacheEntity {

    private String name;
    @ManyToOne(optional = false)
    private Course course;

    // required for JPA
    protected Lesson() {
    }

    public Lesson(String name, Course course) {
        this.name = name;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
