package com.znamen.tones.application.use_case;

import com.znamen.tones.application.dto.input.SingRequest;
import com.znamen.tones.application.dto.output.SingResponse;
import com.znamen.tones.application.service.ToneSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SingUseCase {
    private final ToneSequenceService sequenceService;

    public SingUseCase(ToneSequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    public SingResponse execute(SingRequest request) {
        List<String> chantLines = getNormalizedChantLines(request.chant());
        List<String> sequence = sequenceService.getSequence(request.tone(), chantLines.size());

        return new SingResponse(
                chantLines,
                sequence
        );
    }

    private List<String> getNormalizedChantLines(String chant) {
        String normalizedChant = chant.strip();
        normalizedChant = normalizedChant.replaceAll("\\s+", " ");
        normalizedChant = normalizedChant.replaceAll("/+", "/");
        normalizedChant = normalizedChant.replaceAll(" / | /|/ ", "/");
        return Arrays.stream(normalizedChant.split("/")).toList();
    }
}
