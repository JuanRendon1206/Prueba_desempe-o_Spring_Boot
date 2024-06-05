package com.riwi.pruebaDesempenoSpringBoot.api.dto.request;

import jakarta.validation.constraints.Email;
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
public class StudentRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "The name must be at most 255 characters.")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "The email must be at most 255 characters.")
    @Email(message = "The email should be valid")
    private String email;

    @NotNull(message = "Class is required")
    private Long class_id;

    @NotNull(message = "Active status is required")
    private Boolean active;
}
