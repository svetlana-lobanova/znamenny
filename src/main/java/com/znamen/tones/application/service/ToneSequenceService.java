package com.znamen.tones.application.service;

import com.znamen.tones.domain.entity.Tone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ToneSequenceService {
    public List<String> getSequence(Integer tone, Integer lineCount) {
        if (lineCount < 2) throw new IllegalArgumentException("Строк должно быть больше одной");
        Tone toneSchema;
        List<String> schema = new ArrayList<>();
        switch (tone) {
            case 1, 8 -> {
                //123123...k
                toneSchema = new Tone(
                        null,
                        Arrays.asList("line1", "line2", "line3"),
                        Arrays.asList("ending1", "ending2", "ending3"),
                        "ending"
                );
                for (int i = 0; i < lineCount - 1; i++) {
                    int stringType = i % 3;
                    schema.add(toneSchema.middleSchemasList.get(stringType));
                }
                //todo: использовать просто ending для коротких строк
                int lastStringType = (lineCount - 1) % 3;
                schema.add(toneSchema.endingSchemasList.get(lastStringType));
            }
            case 2 -> {
                //1234234...k
                toneSchema = new Tone(
                        null,
                        Arrays.asList("line1", "line2", "line3", "line4"),
                        Arrays.asList(null, null, "ending3", null),
                        "ending"
                );
                schema.add(toneSchema.middleSchemasList.get(0));
                if (lineCount > 2) {
                    for (int i = 0; i < lineCount - 2; i++) {
                        int stringType = (i % 3) + 1;
                        schema.add(toneSchema.middleSchemasList.get(stringType));
                    }
                }
                int lastStringType = (lineCount - 1) % 3;
                if (toneSchema.endingSchemasList.get(lastStringType) != null) {
                    schema.add(toneSchema.endingSchemasList.get(lastStringType));
                } else {
                    schema.add(toneSchema.endingSchema);
                }
            }
            case 3, 7 -> {
                //1212...k
                toneSchema = new Tone(
                        null,
                        Arrays.asList("line1", "line2"),
                        Arrays.asList("ending1", "ending2"),
                        null
                );
                for (int i = 0; i < lineCount - 1; i++) {
                    int stringType = i % 2;
                    schema.add(toneSchema.middleSchemasList.get(stringType));
                }
                int lastStringType = (lineCount - 1) % 2;
                schema.add(toneSchema.endingSchemasList.get(lastStringType));
            }
            case 4 -> {
                //123434...k
                toneSchema = new Tone(
                        null,
                        Arrays.asList("line1", "line2", "line3", "line4"),
                        Arrays.asList(null, null, "ending3", "ending4"),
                        "ending"
                );
                schema.add(toneSchema.middleSchemasList.get(0));
                if (lineCount > 2) {
                    schema.add(toneSchema.middleSchemasList.get(1));
                }
                if (lineCount > 3) {
                    for (int i = 0; i < lineCount - 3; i++) {
                        int stringType = (i % 2) + 2;
                        schema.add(toneSchema.middleSchemasList.get(stringType));
                    }
                }
                int lastLineType = ((lineCount - 1) % 2) + 2;
                if (lineCount == 2) lastLineType = 1;
                if (toneSchema.endingSchemasList.get(lastLineType) != null) {
                    schema.add(toneSchema.endingSchemasList.get(lastLineType));
                } else {
                    schema.add(toneSchema.endingSchema);
                }
            }
            case 5 -> {
                //123123...k
                toneSchema = new Tone(
                        "starting",
                        Arrays.asList("line1", "line2", "line3"),
                        Arrays.asList("ending1", "ending2", null),
                        "ending"
                );
                schema.add(toneSchema.startSchema);
                for (int i = 1; i < lineCount - 1; i++) {
                    int lineType = i % 3;
                    schema.add(toneSchema.middleSchemasList.get(lineType));
                }
                //todo: использовать просто ending для коротких строк
                int lastLineType = (lineCount - 1) % 3;
                if (toneSchema.endingSchemasList.get(lastLineType) != null) {
                    schema.add(toneSchema.endingSchemasList.get(lastLineType));
                } else {
                    schema.add(toneSchema.endingSchema);
                }
            }
            case 6 -> {
                //123123...k
                toneSchema = new Tone(
                        null,
                        Arrays.asList("line1", "line2", "line3"),
                        Arrays.asList(null, null, null),
                        "ending"
                );
                for (int i = 0; i < lineCount - 1; i++) {
                    int stringType = i % 3;
                    schema.add(toneSchema.middleSchemasList.get(stringType));
                }
                schema.add(toneSchema.endingSchema);
            }
            default -> throw new IllegalArgumentException("Передан неверный номер гласа");
        }

        return schema;
    }
}
