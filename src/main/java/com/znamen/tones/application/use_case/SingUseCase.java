package com.znamen.tones.application.use_case;

import com.znamen.tones.application.dto.input.SingRequest;
import com.znamen.tones.application.dto.output.SingResponse;
import com.znamen.tones.application.service.ToneSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SingUseCase {
    private final ToneSequenceService sequenceService;

    public SingUseCase(ToneSequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    public SingResponse execute(SingRequest request) {
        List<String> sequence = sequenceService.getSequence(request.tone(), request.lineCount());

        return new SingResponse(
                request.chant(),
                sequence
        );
    }
}
