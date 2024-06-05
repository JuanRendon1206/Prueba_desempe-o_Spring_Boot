package com.riwi.pruebaDesempenoSpringBoot.utils.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}