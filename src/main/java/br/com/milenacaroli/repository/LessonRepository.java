package br.com.milenacaroli.repository;

import br.com.milenacaroli.entity.Lesson;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LessonRepository implements PanacheRepository<Lesson> {

    public List<Lesson> findByCourseId(Long courseId) {
        return list("course.id", courseId);
    }
}
