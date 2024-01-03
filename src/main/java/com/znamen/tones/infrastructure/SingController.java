package com.znamen.tones.infrastructure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SingController {

    @GetMapping("/sing")
    public String execute() {
        return "la";
    }
}
