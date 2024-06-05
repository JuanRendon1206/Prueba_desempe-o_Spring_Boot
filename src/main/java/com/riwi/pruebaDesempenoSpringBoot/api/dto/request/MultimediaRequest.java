package com.riwi.pruebaDesempenoSpringBoot.api.dto.request;

import com.riwi.pruebaDesempenoSpringBoot.utils.enums.MultimediaType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaRequest {
    
    @NotNull(message = "Multimedia type is required")
    private MultimediaType type;

    @NotBlank(message = "URL is required")
    private String url;

    @NotNull(message = "Lesson is required")
    private Long lesson_id;

    @NotNull(message = "Active status is required")
    private Boolean active;
}
