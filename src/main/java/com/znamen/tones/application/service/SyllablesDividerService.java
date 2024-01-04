package com.znamen.tones.application.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SyllablesDividerService {
    private static final String VOWELS = "аеиоуяэыёю";
    private static final String CONSONANTS = "бвгджзйклмнпрстфхцчшщьъ";

    // Певческое деление на слоги - слог всегда заканчивается на гласной.
    // Дополнительно учитывается наличие знаков препинания и ударений.
    public static List<String> divideIntoSyllables(String word) {
        List<String> syllables = new ArrayList<>();
        StringBuilder syllable = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);

            // Если текущий символ - гласная
            if (isVowel(currentChar)) {
                syllable.append(currentChar);

                // Если следующий символ - знак препинания, ударение
                if (i + 1 < word.length() && (notIsLetter(word.charAt(i + 1)))) {
                    syllable.append(word.charAt(i + 1));
                    i++; // Пропускаем уже добавленный символ

                    if (i + 1 < word.length() && (notIsLetter(word.charAt(i + 1)))) {
                        syllable.append(word.charAt(i + 1));
                        i++; // Пропускаем уже добавленный символ
                    }
                }

                syllables.add(syllable.toString());
                syllable.setLength(0); // Очищаем буфер слога
            }
            // Если текущий символ - не гласная
            else if (!isVowel(currentChar)) {
                syllable.append(currentChar);
            }
        }

        // Добавляем последний слог, если он остался
        if (syllable.length() > 0) {
            syllables.add(syllable.toString());
        }

        //если последний слог остался без гласной, соединяем его с предпоследним
        String lastSyllable = syllables.get(syllables.size()-1);
        if (syllables.size() > 1 && !StringUtils.containsAny(lastSyllable, VOWELS)) {
            syllables.set(syllables.size()-2, syllables.get(syllables.size()-2) + lastSyllable);
            syllables.remove(syllables.size()-1);
        }

        return syllables;
    }

    private static boolean isVowel(char c) {
        return VOWELS.contains(String.valueOf(Character.toLowerCase(c)));
    }

    private static boolean isConsonant(char c) {
        return CONSONANTS.contains(String.valueOf(Character.toLowerCase(c)));
    }

    private static boolean notIsLetter(char c) {
        return !isVowel(c) && !isConsonant(c);
    }


}
