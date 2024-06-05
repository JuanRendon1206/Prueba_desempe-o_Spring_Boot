package com.riwi.pruebaDesempenoSpringBoot.infraestructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.pruebaDesempenoSpringBoot.api.dto.request.LessonRequest;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.request.MultimediaRequest;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.LessonResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.MultimediaBasicResponse;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.ClassEntity;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Lesson;
import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Multimedia;
import com.riwi.pruebaDesempenoSpringBoot.domain.repositories.ClassRepository;
import com.riwi.pruebaDesempenoSpringBoot.domain.repositories.LessonRepository;
import com.riwi.pruebaDesempenoSpringBoot.domain.repositories.MultimediaRepository;
import com.riwi.pruebaDesempenoSpringBoot.utils.exceptions.BadRequestException;
import com.riwi.pruebaDesempenoSpringBoot.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {
    
    @Autowired
    private final LessonRepository repository;

    @Autowired
    private final ClassRepository classRepository;

    @Autowired
    private final MultimediaRepository multimediaRepository;

    public LessonResponse get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    public LessonResponse create(LessonRequest request) {
        Lesson lesson = requestToEntity(request);
        this.repository.save(lesson);
    
        if(request.getMultimedias() != null) {
            request.getMultimedias().stream().forEach(multimedia -> {
                Multimedia multimediaNew = 
                Multimedia.builder()
            .type(multimedia.getType())
            .url(multimedia.getUrl())
            .active(multimedia.getActive())
            .lesson_id(lesson)
            .build();
    
            this.multimediaRepository.save(multimediaNew);
            });
        } else {
            lesson.setMultimedias(new ArrayList<>());
        }
    
        return this.entityToResponse(lesson);
    }

    public LessonResponse disable(Long id) {
        Lesson lesson = this.find(id);

        lesson.setActive(false);

        return this.entityToResponse(this.repository.save(lesson));
    }

    private LessonResponse entityToResponse(Lesson entity) {
        List<MultimediaBasicResponse> multimedias = 
            entity.getMultimedias()
            .stream()
            .map(this::multimediaEntityToResponse)
            .collect(Collectors.toList());        

        return LessonResponse.builder()
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

    private Lesson requestToEntity(LessonRequest request) {
        List<Multimedia> multimedias = 
            request.getMultimedias()
            .stream()
            .map(this::multimediaRequestToEntity)
            .collect(Collectors.toList()); 

        return Lesson.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .active(request.getActive())
                .class_id(findClass(request.getClass_id()))
                .multimedias(multimedias)
                .build();
    }

    private Multimedia multimediaRequestToEntity(MultimediaRequest request) {
        return Multimedia.builder()
                .type(request.getType())
                .url(request.getUrl())
                .active(request.getActive())
                .lesson_id(find(request.getLesson_id()))
                .build();
    }

    private Lesson find(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Lesson")));
    }

    private ClassEntity findClass(Long id) {
        return this.classRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Class")));
    }    
}
