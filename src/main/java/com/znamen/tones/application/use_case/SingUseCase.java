package com.znamen.tones.application.use_case;

import com.znamen.tones.application.dto.input.SingRequest;
import com.znamen.tones.application.dto.output.SingResponse;
import com.znamen.tones.application.service.SyllablesDividerService;
import com.znamen.tones.application.service.ToneSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SingUseCase {
    private final ToneSequenceService sequenceService;
    private final SyllablesDividerService syllablesDividerService;

    public SingUseCase(ToneSequenceService sequenceService, SyllablesDividerService syllablesDividerService) {
        this.sequenceService = sequenceService;
        this.syllablesDividerService = syllablesDividerService;
    }

    public SingResponse execute(SingRequest request) {
        //нормализуем песнопение и делим на строки
        List<String> chantLines = getNormalizedChantLines(request.chant());
        List<String> sequence = sequenceService.getSequence(request.tone(), chantLines.size());

        //список строк со списком слогов каждой строки, для использования в распевке
        List<List<String>> chantLinesBySyllables = new ArrayList<>();
        //список строк песнопения со словами, разделенными на слоги,
        //для использования в формировании файла lilypond
        List<String> formattedChantLines = new ArrayList<>();
        //делим строки на слова
        for (String line : chantLines) {
            List<List<String>> lineByWords = new ArrayList<>();
            List<String> formattedLineWords = new ArrayList<>();
            List<String> words = getLineWords(line);
            //делим слова на слоги
            for (String word : words) {
                List<String> syllables = SyllablesDividerService.divideIntoSyllables(word);
                lineByWords.add(syllables);
                formattedLineWords.add(joinWord(syllables));
            }
            chantLinesBySyllables.add(collectLineSyllables(lineByWords));
            formattedChantLines.add(connectPrepositions(joinLine(formattedLineWords)));
        }

        return new SingResponse(
                formattedChantLines,
                chantLinesBySyllables,
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

    private List<String> getLineWords(String line) {
        return Arrays.stream(line.split(" ")).toList();
    }

    private String joinWord(List<String> wordSyllables) {
        return String.join(" -- ", wordSyllables);
    }

    private String joinLine(List<String> lineWords) {
        return String.join(" ", lineWords);
    }

    private String connectPrepositions(String line) {
        line = line.replaceAll(" к ", " к_");
        line = line.replaceAll(" с ", " с_");
        line = line.replaceAll(" в ", " в_");
        line = line.replaceAll(" К ", " К_");
        line = line.replaceAll(" С ", " С_");
        line = line.replaceAll(" В ", " В_");
        return line;
    }

    private List<String> collectLineSyllables(List<List<String>> line) {
        return line.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
