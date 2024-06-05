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

    @Operation(summary = "Lists all classes by name or description in paginated form as long as the class is active.", description = "You must send the page, the size of the page, and the name or description by which you want to search for the class.")
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
        description = "When the id is not valid.", 
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        }    
    )
    
    @Operation(summary = "Lists a class by specific id.", description = "You must send the id of the class you want to list.")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ClassToLessonResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @Operation(summary = "Create a class.", description = "You must enter the name, description and status of the class.")
    @PostMapping
    public ResponseEntity<ClassBasicResponse> create(@Validated @RequestBody ClassRequest request) {
        return ResponseEntity.ok(this.service.create(request));
    }

}