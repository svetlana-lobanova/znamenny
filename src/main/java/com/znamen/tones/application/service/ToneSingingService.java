package com.znamen.tones.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Map.entry;

@Service
@Slf4j
public class ToneSingingService {
    public String singToneLine(Integer tone, String lineType, List<String> syllables) {
        HashMap<String, Map<String, List<String>>> tone1 = new HashMap<>();
        tone1.put("line1",
                Map.ofEntries(
                        entry("s1", Arrays.asList("e2 (f4 g)")),
                        entry("s2", Arrays.asList("d4", "e2 (f4 g)")),
                        entry("s3", Arrays.asList("e4", "d4", "e2 (f4 g)")),
                        entry("sr", Arrays.asList("e4")),
                        entry("mr", Arrays.asList("e4")),
                        entry("preEnd", Arrays.asList("d4")),
                        entry("e2", Arrays.asList("e2", "e4 (d2)")),
                        entry("e3", Arrays.asList("e2", "e4", "e4 (d2)")),
                        entry("e4", Arrays.asList("e2", "e4", "e4", "e4 (d2)")),
                        entry("e12", Arrays.asList("e2", "e4 (d2 f4)")),
                        entry("e13", Arrays.asList("e2", "e4", "e4 (d2 f4)")),
                        entry("e14", Arrays.asList("e2", "e4", "e4", "e4 (d2 f4)")),
                        entry("e15", Arrays.asList("e2", "e4", "e4", "e4", "e4 (d2 f4)")),
                        entry("e16", Arrays.asList("e2", "e4", "e4", "e4", "e4", "e4 (d2 f4)")),
                        entry("e17", Arrays.asList("e2", "e4", "e4", "e4", "e4", "e4", "e4 (d2 f4)"))
                )
        );


        List<String> lineNotes = new ArrayList<>();

        int firstAccentPosition = getFirstAccentPosition(syllables);
        List<Integer> twoLastAccentPositions = getTwoLastAccentPositions(syllables);

        switch (tone) {
            case 1 -> {
                String startKey="s";
                int startRepeatableCount = 0;
                if (firstAccentPosition<=2) {
                    startKey += String.valueOf(firstAccentPosition + 1);
                } else {
                    startKey += "3";
                    startRepeatableCount = firstAccentPosition - 2;
                }
                for (int i = 0; i<startRepeatableCount; i++) {
                    lineNotes.addAll(tone1.get(lineType).get("sr"));
                }
                lineNotes.addAll(tone1.get(lineType).get(startKey));


                List<String> ending;
                String endKey = "e";
                if (twoLastAccentPositions.get(0) == 0) {
                    endKey += "1";
                    endKey +=  String.valueOf(twoLastAccentPositions.get(1) + 1);
                } else {
                    endKey +=  String.valueOf(twoLastAccentPositions.get(0) + 1);
                }
                ending = tone1.get(lineType).get(endKey);

                int remainer = syllables.size()-lineNotes.size()-ending.size();

                if (remainer<0) {
                    throw new IllegalArgumentException("В строке слишком мало слогов");
                }
                if (remainer>1) {
                    for (int i=1; i<remainer; i++) {
                        lineNotes.add(tone1.get(lineType).get("mr").get(0));
                    }
                    lineNotes.add(tone1.get(lineType).get("preEnd").get(0));
                }
                lineNotes.addAll(ending);
                //lineNotes.add("\\bar\"|\"");
            }
            default -> {
                throw new IllegalArgumentException("Передан неверный номер гласа");
            }
        }
        return String.join(" ", lineNotes);
    }

    private int getFirstAccentPosition(List<String> syllables) {
        for (String syllable : syllables) {
            if (syllable.contains("́")) {
                return syllables.indexOf(syllable);
            }
        }
        return -1;
    }

    private List<Integer> getTwoLastAccentPositions(List<String> syllables) {
        int lastAccentPosition = -1;
        int secondToLastAccentPosition = -1;
        Collections.reverse(syllables);
        for (String syllable : syllables) {
            if (syllable.contains("́")) {
                if (lastAccentPosition != -1 && secondToLastAccentPosition == -1) {
                    secondToLastAccentPosition = syllables.indexOf(syllable);
                }
                if (lastAccentPosition == -1) {
                    lastAccentPosition = syllables.indexOf(syllable);
                }
            }
        }
        return Arrays.asList(lastAccentPosition, secondToLastAccentPosition);
    }
}
