package com.counter.words.utils;

import com.counter.words.model.enums.MediaMimeType;
import com.counter.words.model.records.WordCountRecord;
import com.counter.words.model.records.WordGroupDtoRecord;
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

    public static WordCountRecord buildWordCount(Map.Entry<String, Long> entry) {
        return WordCountRecord.builder()
                .word(entry.getKey())
                .count(entry.getValue())
                .build();
    }

    public static WordGroupDtoRecord buildWordGroupDto(Map.Entry<String, List<WordCountRecord>> entry) {
        return WordGroupDtoRecord.builder()
                .name(entry.getKey())
                .words(entry.getValue())
                .build();
    }


}
