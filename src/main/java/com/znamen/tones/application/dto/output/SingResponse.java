package com.znamen.tones.application.dto.output;

import java.util.List;

public record SingResponse(
        List<String> chantLines,
        List<String> sequence
) {
}
