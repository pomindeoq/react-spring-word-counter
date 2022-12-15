package com.counter.words.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextUtilsTest {

    @Test
    void removeSpecialCharactersShouldRemoveAllSingleDoubleQuotes() {
        String text = "I'm a text with 'single' and \"double\" quotes";
        String result = TextUtils.removeSpecialCharacters(text);
        assertEquals("Im a text with single and double quotes", result);
    }

    @Test
    void removeSpecialCharactersShouldRemoveAllNumbers() {
        String text = "1234567890";
        String result = TextUtils.removeSpecialCharacters(text);
        assertEquals("", result);
    }

    @Test
    void removeSpecialCharactersShouldRemoveAllSpecialCharacters() {
        String text = "!@#$%^&*()_+-=[]{}|;':\",./<>?";
        String result = TextUtils.removeSpecialCharacters(text);
        assertEquals("", result);
    }
}