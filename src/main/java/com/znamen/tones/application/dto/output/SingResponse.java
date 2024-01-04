package com.znamen.tones.application.dto.output;

import java.util.List;

public record SingResponse(
        List<String> formattedLines,
        List<List<String>> linesSyllables,
        List<String> sequence
) {
}
