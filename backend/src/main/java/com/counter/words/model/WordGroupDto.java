package com.counter.words.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class WordGroupDto {
    String name;
    List<WordCount> words;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordGroupDto that = (WordGroupDto) o;
        return Objects.equals(name, that.name) && Objects.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, words);
    }
}
