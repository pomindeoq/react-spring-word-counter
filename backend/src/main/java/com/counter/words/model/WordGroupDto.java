package com.counter.words.model;

import lombok.Builder;
import java.util.List;

@Builder
public record WordGroupDto(String name, List<WordCount> words) {}