package br.com.milenacaroli.service;

import br.com.milenacaroli.dto.CourseDTO;
import br.com.milenacaroli.dto.LessonDTO;
import br.com.milenacaroli.entity.Course;
import br.com.milenacaroli.entity.Lesson;
import br.com.milenacaroli.exception.CourseNotFoundException;
import br.com.milenacaroli.repository.CourseRepository;
import br.com.milenacaroli.repository.LessonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CourseService {

    @Inject //service depende do repository
    private CourseRepository courseRepository;

    @Inject
    private LessonRepository lessonRepository;

    // Operações com Course

    @Transactional
    public CourseDTO create(CourseDTO courseDTO) {//criando curso
        Course course = CourseDTO.toEntity(courseDTO);
        course.persist();//gravando curso
        return CourseDTO.fromEntity(course);
    }

    public List<CourseDTO> listAll() {
        return courseRepository.listAll() //busca no DB
                .stream()//trata dados
                .map(CourseDTO::fromEntity)//converte course (entidade) em DTO
                .toList(); //devolve a lista
    }

    public CourseDTO findById(Long id) {//dado um ID, devolve um curso com esse ID
        return courseRepository.findByIdOptional(id) //busca pelo ID (pode não existir)
                .map(CourseDTO::fromEntity) //se encontrar, mapeia o retorno para DTO
                .orElseThrow(() -> new CourseNotFoundException(id)); // se não existir, lança exceção
    }

    @Transactional //DB
    public CourseDTO update(Long id, CourseDTO courseDTO) {
        return courseRepository.findByIdOptional(id) //busca curso (pode nao existir (optional))
                .map(course -> { //se encontrar, atualizar o nome com o nome do DTO
                    //encontrou curso
                    course.setName(courseDTO.name()); //pegando nome do DTO e atualizando o nome no DB
                    return CourseDTO.fromEntity(course);//retorno do curso atualizado
                })
                .orElseThrow(() -> new CourseNotFoundException(id)); //lança excecao e sai do metodo
    }

    @Transactional
    public void delete(Long id) {//void nao retorna nada
        Course course = courseRepository.findByIdOptional(id) //encontra curso
                .orElseThrow(() -> new CourseNotFoundException(id));//se não encontrar, lança exceção e sai
        courseRepository.delete(course);//se encontrou, apaga
    }

    // operaçoes com Lesson

    @Transactional
    public LessonDTO addLesson(Long courseId, LessonDTO lessonDTO) {//adicionando Lição
        return courseRepository.findByIdOptional(courseId)//procura curso
                .map(course -> {
                    Lesson lesson = new Lesson();
                    lesson.setName(lessonDTO.name());
                    lesson.setCourse(course);
                    lessonRepository.persist(lesson); //grava lição
                    return LessonDTO.fromEntity(lesson);
                })
                .orElseThrow(() -> new CourseNotFoundException(courseId)); //se não encontrar lança exceção
    }

    public List<LessonDTO> listLessons(Long courseId) { //lista lições de um curso
        courseRepository.findByIdOptional(courseId) //busca curso
                .orElseThrow(() -> new CourseNotFoundException(courseId)); //se  não encontrar, lança exceção
        return lessonRepository.findByCourseId(courseId)//achou curso, consulta lessons daquele curso
                .stream()
                .map(LessonDTO::fromEntity)//mapeia lições das entidades para DTO e devolve
                .toList();
    }
}
