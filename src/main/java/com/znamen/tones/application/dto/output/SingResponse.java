package com.znamen.tones.application.dto.output;

import java.util.List;

public record SingResponse(
        String chant,
        List<String> sequence
) {
}
