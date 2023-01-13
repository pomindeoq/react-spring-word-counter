package com.counter.words.model.records;

import com.counter.words.model.Groupable;
import com.counter.words.model.enums.WordGroup;
import lombok.Builder;

import java.util.EnumSet;

@Builder
public record WordCountRecord(String word, Long count) implements Groupable {
    @Override
    public String getGroup() {
        if (this.word == null || this.word.length() == 0) {
            return null;
        }
        return EnumSet.allOf(WordGroup.class).stream()
                .filter(g -> g.getLettersString().contains(this.word.substring(0, 1)))
                .map(WordGroup::getName)
                .findFirst()
                .orElse(null);
    }
}
