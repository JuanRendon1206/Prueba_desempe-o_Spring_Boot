package com.riwi.pruebaDesempenoSpringBoot.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "The title must be at most 255 characters.")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Class is required")
    private Long class_id;
}
