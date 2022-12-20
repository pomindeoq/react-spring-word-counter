package com.counter.words.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WordGroupDto {
    String name;
    List<WordCount> words;
}
