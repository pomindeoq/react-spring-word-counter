package com.counter.words.model;

import com.counter.words.model.records.WordCountRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WordCountTest {

    @Test
    void whenWordIsNullThenReturnNull() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word(null).build();
        assertNull(wordCountRecord.getGroup());
    }

    @Test
    void whenWordIsEmptyThenReturnNull() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word("").build();
        assertNull(wordCountRecord.getGroup());
    }

    @Test
    void whenWordStartsWithBThenReturnAG() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word("b").build();
        assertEquals("[A-G]", wordCountRecord.getGroup());
    }

    @Test
    void whenWordStartsWithAThenReturnAG() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word("apple").build();
        assertEquals("[A-G]", wordCountRecord.getGroup());
    }

    @Test
    void whenWordStartsWithShouldReturnHN() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word("kappa").build();
        assertEquals("[H-N]", wordCountRecord.getGroup());
    }

    @Test
    void whenWordStartsWithOShouldReturnOU() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word("orange").build();
        assertEquals("[O-U]", wordCountRecord.getGroup());
    }

    @Test
    void whenWordStartsWithZShouldReturnVZ() {
        WordCountRecord wordCountRecord = WordCountRecord.builder().word("zombie").build();
        assertEquals("[V-Z]", wordCountRecord.getGroup());
    }
}