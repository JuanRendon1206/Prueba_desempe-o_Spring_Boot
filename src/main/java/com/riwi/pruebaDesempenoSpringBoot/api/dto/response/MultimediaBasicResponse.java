package com.riwi.pruebaDesempenoSpringBoot.api.dto.response;

import java.time.LocalDateTime;

import com.riwi.pruebaDesempenoSpringBoot.utils.enums.MultimediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaBasicResponse {

    private Long id;
    private MultimediaType type;
    private String url;
    private LocalDateTime created_at;
    private Boolean active;
}
