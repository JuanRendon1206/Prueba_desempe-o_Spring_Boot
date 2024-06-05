package com.riwi.pruebaDesempenoSpringBoot.api.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LessonBasicResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private Boolean active;
}
