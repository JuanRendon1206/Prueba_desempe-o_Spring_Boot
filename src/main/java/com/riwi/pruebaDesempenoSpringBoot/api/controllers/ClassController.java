package com.riwi.pruebaDesempenoSpringBoot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.pruebaDesempenoSpringBoot.api.dto.request.ClassRequest;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.ClassBasicResponse;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.ClassToLessonResponse;
import com.riwi.pruebaDesempenoSpringBoot.infraestructure.services.ClassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/class")
@AllArgsConstructor
@Tag(name = "Classes")
public class ClassController {

    @Autowired
    private final ClassService service;

    @Operation(summary = "Lista todas las clases por nombre o descripción de forma paginada siempre y cuando la clase este activa.", description = "Debes enviar la página, el tamaño de la página, y el nombre o la descripción por la que quieres buscar la clase.")
    @GetMapping
    public ResponseEntity<Page<Object>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String description
    ){
        return ResponseEntity.ok(this.service.getAll(page - 1, size, name, description));
    }

    @ApiResponse(
        responseCode = "400", 
        description = "Cuando el id no es válido.", 
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        }    
    )
    
    @Operation(summary = "Lista una clase por el id específico.", description = "Debes enviar el id de la clase que deseas listar.")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ClassToLessonResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @Operation(summary = "Crea una clase.", description = "Debes ingresar el nombre, la descripción y el estado de la clase.")
    @PostMapping
    public ResponseEntity<ClassBasicResponse> create(@Validated @RequestBody ClassRequest request) {
        return ResponseEntity.ok(this.service.create(request));
    }

}