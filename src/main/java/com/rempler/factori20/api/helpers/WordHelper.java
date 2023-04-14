package com.rempler.factori20.api.helpers;

public class WordHelper {
    public static String capitalizeFully(String string) {
        String[] words = string.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return output.toString().trim();
    }
}
