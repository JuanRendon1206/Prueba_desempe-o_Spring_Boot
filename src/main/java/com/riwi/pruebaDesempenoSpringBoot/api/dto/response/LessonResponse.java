package com.riwi.pruebaDesempenoSpringBoot.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponse extends LessonBasicResponse{
    
    private ClassToLessonResponse class_id;
    private List<MultimediaBasicResponse> multimedias;
}
