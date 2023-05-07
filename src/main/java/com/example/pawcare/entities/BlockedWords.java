package com.example.pawcare.entities;

import java.util.Arrays;
import java.util.List;

public class BlockedWords {
    private static final List<String> blockedWords = Arrays.asList("bad", "evil", "hate");

    public static String replaceBlockedWords(String content) {
        for (String blockedWord : blockedWords) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < blockedWord.length(); i++) {
                sb.append("*");
            }
            String replacement = sb.toString();
            content = content.replaceAll(blockedWord, replacement);
        }
        return content;
    }

}

