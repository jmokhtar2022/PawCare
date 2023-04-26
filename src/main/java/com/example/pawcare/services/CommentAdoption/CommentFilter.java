package com.example.pawcare.services.CommentAdoption;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class CommentFilter {
    private static final Set<String> BAD_WORDS = new HashSet<>(Arrays.asList("bad", "words"));

    public String filter(String text) {
        for (String word : BAD_WORDS) {
            text = text.replaceAll(word, "***");
        }
        return text;
    }
}
