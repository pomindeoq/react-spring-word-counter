package com.counter.words.model;

import lombok.Builder;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Objects;

@Builder
@Getter
public class WordCount {
    String word;
    Long count;

    public String getGroup() {
        if(this.word == null || this.word.length() == 0) {
            return null;
        }
        return EnumSet.allOf(WordGroup.class).stream()
                .filter(g -> g.getLettersString().contains(this.word.substring(0,1)))
                .map(WordGroup::getName)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCount wordCount = (WordCount) o;
        return Objects.equals(word, wordCount.word) && Objects.equals(count, wordCount.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count);
    }
}
