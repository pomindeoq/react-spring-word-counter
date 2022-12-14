package com.counter.words.utils;

import com.counter.words.model.MediaMimeType;
import com.counter.words.model.WordCount;
import com.counter.words.model.WordGroup;
import com.counter.words.model.WordGroupDto;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@UtilityClass
public class FileUtils {
    public static List<MultipartFile> filterWrongType(List<MultipartFile> files) {
        return files.stream()
                .filter(f -> Objects.equals(f.getContentType(), MediaMimeType.TEXT_PLAIN.getMimeType()))
                .toList();
    }
    public static List<MultipartFile> filterEmpty(MultipartFile[] files) {
        return Arrays.stream(files)
                .filter(Predicate.not(MultipartFile::isEmpty))
                .toList();
    }

    public static WordCount buildWordCount(Map.Entry<String, Long> entry) {
        return WordCount.builder()
                .word(entry.getKey())
                .count(entry.getValue())
                .build();
    }

    public static WordGroupDto buildWordGroupDto(Map.Entry<String, List<WordCount>> entry) {
        return WordGroupDto.builder()
                .name(entry.getKey())
                .words(entry.getValue())
                .build();
    }


}
