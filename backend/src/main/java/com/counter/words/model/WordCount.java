package com.counter.words.model;

import lombok.Builder;
import lombok.Getter;

import java.util.EnumSet;

@Builder
@Getter
public class WordCount {
    String word;
    Long count;

    public String getGroup() {
        return EnumSet.allOf(WordGroup.class).stream()
                .filter(g -> g.getLettersString().contains(this.word.substring(0,1)))
                .map(WordGroup::getName)
                .findFirst()
                .orElse(null);
    }
}
