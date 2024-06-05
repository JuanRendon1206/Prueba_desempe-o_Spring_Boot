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
public class StudentBasicResponse {
    
    private Long id;
    private String name;
    private String email;
    private LocalDateTime created_at;
    private Boolean active;
}
