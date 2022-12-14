package com.counter.words.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TextUtils {
    public static String removeSpecialCharacters(String text) {
        return text.replace("'", "").replaceAll("[^\\w\\s]", "").replace("_", "").replaceAll("\\d", "");
    }
}