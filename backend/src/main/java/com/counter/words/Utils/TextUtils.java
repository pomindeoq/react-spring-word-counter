package com.counter.words.Utils;

public class TextUtils {
    public static String removeSpecialCharacters(String text) {
        return text.replace("'", "").replaceAll("[^\\w\\s]", "").replace("_", "").replaceAll("\\d", "");
    }
}