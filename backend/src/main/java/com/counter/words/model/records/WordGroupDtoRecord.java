package com.counter.words.model.records;

import lombok.Builder;
import java.util.List;

@Builder
public record WordGroupDtoRecord(String name, List<WordCountRecord> words) {}