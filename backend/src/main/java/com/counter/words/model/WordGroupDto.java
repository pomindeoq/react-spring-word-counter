package com.counter.words.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WordGroupDto {
    String name;

    List<WordCount> words;
}
