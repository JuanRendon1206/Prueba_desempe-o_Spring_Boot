package com.riwi.pruebaDesempenoSpringBoot.infraestructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.pruebaDesempenoSpringBoot.api.dto.request.ClassRequest;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.ClassBasicResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.ClassToLessonResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.StudentBasicResponse;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.ClassEntity;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Student;
import com.riwi.pruebaDesempenoSpringBoot.domain.repositories.ClassRepository;
import com.riwi.pruebaDesempenoSpringBoot.utils.exceptions.BadRequestException;
import com.riwi.pruebaDesempenoSpringBoot.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClassService {

    @Autowired
    private final ClassRepository repository;

    public Page<Object> getAll(int page, int size, String name, String description) {

        if (page < 0)
            page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.repository.findByNameContainingAndDescriptionContainingAndActive(name, description, pagination, true)
                .map(clase -> this.entityBasicToResponse(clase));
    }

    public ClassToLessonResponse get(Long id) {
        return this.entityGetToResponse(this.find(id));
    }

    public ClassBasicResponse create(ClassRequest request) {

        ClassEntity clase = requestToEntity(request);

        clase.setLessons(new ArrayList<>());
        clase.setStudents(new ArrayList<>());

        return this.entityBasicToResponse(this.repository.save(clase));
    }

    private ClassBasicResponse entityBasicToResponse(ClassEntity entity) {
        return ClassBasicResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .build();
    }

    private ClassToLessonResponse entityGetToResponse(ClassEntity entity) {
        List<StudentBasicResponse> students = entity.getStudents()
                .stream()
                .map(this::studentEntityToResponse)
                .collect(Collectors.toList());

        return ClassToLessonResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .students(students)
                .build();
    }

    private StudentBasicResponse studentEntityToResponse(Student entity) {
        return StudentBasicResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .created_at(entity.getCreated_at())
                .active(entity.getActive())
                .build();
    }

    private ClassEntity requestToEntity(ClassRequest request) {

        System.out.println(ClassEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(request.getActive())
                .build());

        return ClassEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(request.getActive())
                .build();
    }

    private ClassEntity find(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Class")));
    }

}