package com.znamen.tones.infrastructure.controller;

import com.znamen.tones.application.dto.input.SingRequest;
import com.znamen.tones.application.dto.output.SingResponse;
import com.znamen.tones.application.use_case.SingUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SingController {
    private final SingUseCase useCase;

    public SingController(SingUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("api/v1/sing")
    public ResponseEntity<SingResponse> execute(@RequestBody SingRequest request) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(useCase.execute(request));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
        }
    }
}
