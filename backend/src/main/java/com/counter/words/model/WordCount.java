package com.counter.words.model;

import lombok.Builder;
import lombok.Data;

import java.util.EnumSet;

@Builder
@Data
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
}
