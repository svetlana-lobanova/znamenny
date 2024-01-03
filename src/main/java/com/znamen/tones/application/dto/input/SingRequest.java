package com.znamen.tones.application.dto.input;

public record SingRequest(
        String title,
        Integer tone,
        String chant,
        Integer lineCount
) {
}
