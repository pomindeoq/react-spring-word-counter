package com.counter.words.service;

import com.counter.words.exception.constants.ErrorCodes;
import com.counter.words.model.WordCount;
import com.counter.words.model.WordGroupDto;
import com.counter.words.utils.FileUtils;
import com.counter.words.utils.TextUtils;
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
    public List<WordGroupDto> parseFile(MultipartFile[] files) {
        Map<String, Long> wordsCount = getValidFiles(files).stream()
                .map(this::getWordCount)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, Long::sum));

        Map<String, List<WordCount>> wordGroupMap = wordsCount.entrySet().stream()
                .map(FileUtils::buildWordCount)
                .collect(Collectors.groupingBy(WordCount::getGroup, TreeMap::new, Collectors.toList()));

        return wordGroupMap.entrySet().stream()
                .map(FileUtils::buildWordGroupDto)
                .toList();
    }

    private Map<String, Long> getWordCount(MultipartFile file) {
        try {
            return new BufferedReader(new InputStreamReader((file.getInputStream()), StandardCharsets.UTF_8))
                    .lines()
                    .map(this::handleLine)
                    .flatMap((List::stream))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } catch (IOException e) {
            throw ErrorCodes.FILES_DATA_INVALID.getException();
        }
    }

    private List<MultipartFile> getValidFiles(MultipartFile[] files) {
        if (files == null) {
            throw ErrorCodes.FILES_MISSING.getException();
        }
        List<MultipartFile> notEmptyFiles = FileUtils.filterEmpty(files);
        if (notEmptyFiles.isEmpty()) {
            throw ErrorCodes.FILES_EMPTY.getException();
        }
        List<MultipartFile> validTypeFiles = FileUtils.filterWrongType(notEmptyFiles);
        if (validTypeFiles.isEmpty()) {
            throw ErrorCodes.FILES_TYPE_NOT_VALID.getException();
        }
        return validTypeFiles;
    }

    private List<String> handleLine(String line) {
        return Arrays.stream(line.split(" "))
                .map(TextUtils::removeSpecialCharacters)
                .filter(word -> word.length() > 1)
                .map(String::toLowerCase)
                .toList();
    }
}
