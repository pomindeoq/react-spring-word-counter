package com.counter.words.service;

import com.counter.words.Utils.TextUtils;
import com.counter.words.exception.constants.ErrorCodes;
import com.counter.words.model.MediaMimeType;
import com.counter.words.model.WordCount;
import com.counter.words.model.WordGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    public List<WordGroupDto> parseFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw ErrorCodes.FILE_MISSING.getException();
        }
        if (multipartFile.isEmpty()) {
            throw ErrorCodes.FILE_EMPTY.getException();
        }
        if (!isValidType(multipartFile.getContentType())) {
            throw ErrorCodes.FILE_TYPE_NOT_VALID.getException();
        }
        try {
            Map<String, Long> wordsCount = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .map(this::handleLine)
                    .flatMap((List::stream))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            Map<String, List<WordCount>> wordGroupMap = wordsCount.entrySet().stream()
                    .map(e -> WordCount.builder()
                            .word(e.getKey())
                            .count(e.getValue())
                            .build())
                    .collect(Collectors.groupingBy(WordCount::getGroup, TreeMap::new, Collectors.toList()));

            return wordGroupMap.entrySet().stream()
                    .map(e ->
                            WordGroupDto.builder()
                                    .name(e.getKey())
                                    .words(e.getValue())
                                    .build())
                    .toList();
        } catch (Exception e) {
            throw ErrorCodes.FILE_DATA_INVALID.getException();
        }
    }

    private List<String> handleLine(String line) {
        return Arrays.stream(line.split(" "))
                .map(TextUtils::removeSpecialCharacters)
                .filter(word -> word.length() > 1)
                .map(String::toLowerCase)
                .toList();
    }

    private boolean isValidType(String contentType) {
        return EnumSet.allOf(MediaMimeType.class).stream()
                .anyMatch(m -> m.getMimeType().equals(contentType));
    }
}
