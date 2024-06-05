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
public class ClassRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255, message = "The name must be between 1 and 255 characters.")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Active status is required")
    private Boolean active;
}
