package br.com.milenacaroli.service;

import br.com.milenacaroli.dto.CourseRequest;
import br.com.milenacaroli.dto.CourseResponse;
import br.com.milenacaroli.dto.LessonRequest;
import br.com.milenacaroli.dto.LessonResponse;
import br.com.milenacaroli.exception.CourseNotFoundException;
import br.com.milenacaroli.mappers.CourseMapper;
import br.com.milenacaroli.mappers.LessonMapper;
import br.com.milenacaroli.model.Course;
import br.com.milenacaroli.model.Lesson;
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
    public CourseResponse create(CourseRequest courseRequest) {//criando curso
        Course course = CourseMapper.toDomain(courseRequest);
        course.persist();//gravando curso
        return CourseMapper.toResponse(course);
    }

    public List<CourseResponse> listAll() {
        return courseRepository.listAll() //busca no DB
                .stream()//trata dados
                .map(CourseMapper::toResponse)//converte course (entidade) em DTO
                .toList(); //devolve a lista
    }

    public CourseResponse findById(Long id) {//dado um ID, devolve um curso com esse ID
        return courseRepository.findByIdOptional(id) //busca pelo ID (pode não existir)
                .map(CourseMapper::toResponse) //se encontrar, mapeia o retorno para DTO
                .orElseThrow(() -> new CourseNotFoundException(id)); // se não existir, lança exceção
    }

    @Transactional //DB
    public CourseResponse update(Long id, CourseRequest courseRequest) {
        return courseRepository.findByIdOptional(id) //busca curso (pode nao existir (optional))
                .map(course -> { //se encontrar, atualizar o nome com o nome do DTO
                    //encontrou curso
                    course.changeName(courseRequest.name()); //pegando nome do DTO e atualizando o nome no DB
                    return CourseMapper.toResponse(course);//retorno do curso atualizado
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
    public LessonResponse addLesson(Long courseId, LessonRequest lessonRequest) {//adicionando Lição
        return courseRepository.findByIdOptional(courseId)//procura curso
                .map(course -> {
                    Lesson lesson = new Lesson(lessonRequest.name(), course);
                    lessonRepository.persist(lesson); //grava lição
                    return LessonMapper.toResponse(lesson);//devolve licao criada
                })
                .orElseThrow(() -> new CourseNotFoundException(courseId)); //se não encontrar lança exceção
    }

    public List<LessonResponse> listLessons(Long courseId) { //lista lições de um curso
        courseRepository.findByIdOptional(courseId) //busca curso
                .orElseThrow(() -> new CourseNotFoundException(courseId)); //se  não encontrar, lança exceção
        return lessonRepository.findByCourseId(courseId)//achou curso, consulta lessons daquele curso
                .stream()
                .map(LessonMapper::toResponse)//mapeia lições das entidades para DTO e devolve
                .toList();
    }
}
