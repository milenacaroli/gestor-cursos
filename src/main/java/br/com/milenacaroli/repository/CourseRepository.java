package br.com.milenacaroli.repository;

import br.com.milenacaroli.model.Course;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CourseRepository implements PanacheRepository<Course> {

    public List<Course> findAllPaged(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }

    public long countAll() {
        return count();
    }
}
