package com.riwi.pruebaDesempenoSpringBoot.utils.messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ErrorMessages {
    public static String idNotFound(String entity) {
        final String message = "There are no records in entity %s with the supplied id.";
        return String.format(message, entity);
    }
}
