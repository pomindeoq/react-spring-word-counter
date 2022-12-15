package com.counter.words.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WordCountTest {

    @Test
    void whenWordIsNullThenReturnNull() {
        WordCount wordCount = WordCount.builder().word(null).build();
        assertNull(wordCount.getGroup());
    }

    @Test
    void whenWordIsEmptyThenReturnNull() {
        WordCount wordCount = WordCount.builder().word("").build();
        assertNull(wordCount.getGroup());
    }

    @Test
    void whenWordStartsWithBThenReturnAG() {
        WordCount wordCount = WordCount.builder().word("b").build();
        assertEquals("[A-G]", wordCount.getGroup());
    }

    @Test
    void whenWordStartsWithAThenReturnAG() {
        WordCount wordCount = WordCount.builder().word("apple").build();
        assertEquals("[A-G]", wordCount.getGroup());
    }

    @Test
    void whenWordStartsWithShouldReturnHN() {
        WordCount wordCount = WordCount.builder().word("kappa").build();
        assertEquals("[H-N]", wordCount.getGroup());
    }

    @Test
    void whenWordStartsWithOShouldReturnOU() {
        WordCount wordCount = WordCount.builder().word("orange").build();
        assertEquals("[O-U]", wordCount.getGroup());
    }

    @Test
    void whenWordStartsWithZShouldReturnVZ() {
        WordCount wordCount = WordCount.builder().word("zombie").build();
        assertEquals("[V-Z]", wordCount.getGroup());
    }
}