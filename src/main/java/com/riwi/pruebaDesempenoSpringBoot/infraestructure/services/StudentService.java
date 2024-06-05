package com.riwi.pruebaDesempenoSpringBoot.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.pruebaDesempenoSpringBoot.api.dto.request.StudentRequest;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.ClassToStudentResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.LessonToClassResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.MultimediaBasicResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.StudentBasicResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.StudentResponse;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.ClassEntity;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Lesson;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Multimedia;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Student;
import com.riwi.pruebaDesempenoSpringBoot.domain.repositories.ClassRepository;
import com.riwi.pruebaDesempenoSpringBoot.domain.repositories.StudentRepository;
import com.riwi.pruebaDesempenoSpringBoot.utils.exceptions.BadRequestException;
import com.riwi.pruebaDesempenoSpringBoot.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository repository;

    @Autowired
    private final ClassRepository classRepository;

    public Page<Object> getAll(int page, int size, String name) {
        if (page < 0)
            page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.repository.findByNameAndActive(name, pagination, true)
                .map(clase -> this.entityBasicToResponse(clase));
    }

    public StudentResponse get(Long id) {
        return this.entityGetToResponse(this.find(id));
    }

    public StudentBasicResponse create(StudentRequest request) {
        Student student = requestToEntity(request);

        student.setClass_id(findClass(request.getClass_id()));

        return this.entityBasicToResponse(this.repository.save(student));
    }

    public StudentBasicResponse update(StudentRequest request, Long id) {
        Student student = this.find(id);

        Student studentUpdate = this.requestToEntity(request);

        studentUpdate.setId(id);
        studentUpdate.setCreated_at(student.getCreated_at());
        student.setActive(false);

        return this.entityBasicToResponse(this.repository.save(studentUpdate));
    }

    public StudentBasicResponse disable(Long id) {
        Student student = this.find(id);
        student.setActive(false);

        return this.entityBasicToResponse(this.repository.save(student));
    }

    private StudentBasicResponse entityBasicToResponse(Student entity) {
        return StudentBasicResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .build();
    }

    private StudentResponse entityGetToResponse(Student entity) {
        List<LessonToClassResponse> lessons = entity.getClass_id().getLessons()
                .stream()
                .map(this::lessonEntityToResponse)
                .collect(Collectors.toList());

        ClassToStudentResponse clase = ClassToStudentResponse.builder()
                .id(entity.getClass_id().getId())
                .name(entity.getClass_id().getName())
                .description(entity.getClass_id().getDescription())
                .created_at(entity.getClass_id().getCreated_at())
                .active(entity.getClass_id().getActive())
                .lessons(lessons)
                .build();

        return StudentResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .class_id(clase)
                .build();
    }

    private LessonToClassResponse lessonEntityToResponse(Lesson entity) {
        List<MultimediaBasicResponse> multimedias = entity.getMultimedias()
                .stream()
                .map(this::multimediaEntityToResponse)
                .collect(Collectors.toList());

        return LessonToClassResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .multimedias(multimedias)
                .build();
    }

    private MultimediaBasicResponse multimediaEntityToResponse(Multimedia entity) {
        return MultimediaBasicResponse.builder()
                .id(entity.getId())
                .type(entity.getType())
                .url(entity.getUrl())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .build();
    }

    private Student requestToEntity(StudentRequest request) {
        return Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .active(request.getActive())
                .class_id(findClass(request.getClass_id()))
                .build();
    }

    private Student find(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Student")));
    }

    private ClassEntity findClass(Long id) {
        return this.classRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Class")));
    }
}