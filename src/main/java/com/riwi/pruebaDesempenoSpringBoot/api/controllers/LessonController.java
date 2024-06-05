package com.riwi.pruebaDesempenoSpringBoot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.pruebaDesempenoSpringBoot.api.dto.request.LessonRequest;
import com.riwi.pruebaDesempenoSpringBoot.api.dto.response.LessonResponse;
import com.riwi.pruebaDesempenoSpringBoot.infraestructure.services.LessonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/lessons")
@AllArgsConstructor
@Tag(name = "Lessons")
public class LessonController {
    @Autowired
    private final LessonService service;

    @ApiResponse(responseCode = "400", description = "When the id is not valid.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(summary = "Gets a lesson for the specific id.", description = "You must send the id of the lesson you wish to obtain.")
    @GetMapping(path = "/{id}/multimedia")
    public ResponseEntity<LessonResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @Operation(summary = "Create a lesson and its respective multimedia.", description = "You must enter the title, content, status, class and multimedia corresponding to the lesson you want to create.")
    @PostMapping
    public ResponseEntity<LessonResponse> create(@Validated @RequestBody LessonRequest request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @Operation(summary = "Disables a specific lesson.", description = "You must enter the id of the specific lesson you wish to disable.")
    @PatchMapping(path = "/{id}/disable")
    public ResponseEntity<LessonResponse> disable(
        @PathVariable Long id) {
    return ResponseEntity.ok(this.service.disable(id));
    }    
}