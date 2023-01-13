package com.counter.words.controller;

import com.counter.words.WordsApplicationTests;
import com.counter.words.exception.GeneralException;
import com.counter.words.exception.constants.ErrorCodes;
import com.counter.words.model.records.WordCountRecord;
import com.counter.words.model.enums.WordGroup;
import com.counter.words.model.records.WordGroupDtoRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FileControllerTest extends WordsApplicationTests {
    @Autowired
    private ResourceLoader resourceLoader = null;


    @Autowired
    FileController fileController;

    @Test
    void filesMissingShouldThrowException() {
        MultipartFile[] files = null;

        var actualException = assertThrows(GeneralException.class, () -> {
            fileController.uploadFiles(files);
        });

        GeneralException expectedException = ErrorCodes.FILES_MISSING.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
    }

    @Test
    void uploadEmptyFilesShouldThrowException() {
        MultipartFile[] files =
                new MultipartFile[]{
                        new MockMultipartFile("file", "file.txt", "text/plain", "".getBytes()),
                        new MockMultipartFile("file", "file.txt", "text/plain", "".getBytes())
                };

        var actualException = assertThrows(GeneralException.class, () -> {
            fileController.uploadFiles(files);
        });

        var expectedException = ErrorCodes.FILES_EMPTY.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
    }

    @Test
    void uploadInvalidTypeFilesShouldThrowException() {
        MultipartFile[] files =
                new MultipartFile[]{
                        new MockMultipartFile("file", "file.pdf", "application/pdf", "text".getBytes()),
                        new MockMultipartFile("file", "file.json", "application/json", "text".getBytes())
                };

        var actualException = assertThrows(GeneralException.class, () -> {
            fileController.uploadFiles(files);
        });

        var expectedException = ErrorCodes.FILES_TYPE_NOT_VALID.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
    }

    @Test
    void shouldReturnCorrectListOfWordGroups() {
        MultipartFile[] files =
                new MultipartFile[]{
                        new MockMultipartFile("file", "file.txt", "text/plain", "test".getBytes())
                };
        List<WordGroupDtoRecord> wordGroupDtoRecords = List.of(WordGroupDtoRecord.builder()
                .name(WordGroup.OU.getName())
                .words(List.of(WordCountRecord.builder()
                        .word("test")
                        .count(1L)
                        .build()))
                .build());

        var responseEntity = fileController.uploadFiles(files);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wordGroupDtoRecords, responseEntity.getBody());
    }

    @Test
    void emptyAndNotFilesShouldReturnCorrectListOfWordGroups() {
        MultipartFile[] files =
                new MultipartFile[]{
                        new MockMultipartFile("file", "file.txt", "text/plain", "!@#$%^&&test239487';'/.,]][;;".getBytes()),
                        new MockMultipartFile("file", "file.txt", "text/plain", "".getBytes())
                };
        List<WordGroupDtoRecord> wordGroupDtoRecords = List.of(WordGroupDtoRecord.builder()
                .name(WordGroup.OU.getName())
                .words(List.of(WordCountRecord.builder()
                        .word("test")
                        .count(1L)
                        .build()))
                .build());

        var responseEntity = fileController.uploadFiles(files);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wordGroupDtoRecords, responseEntity.getBody());
    }

    @Test
    void shouldParse10000filesAndCountCorrectAmountOfGroupsAndWords() throws IOException {
        File file = resourceLoader.getResource("classpath:testing.txt").getFile();
        MultipartFile[] files = new MultipartFile[10000];

        for (int i = 0; i < 10000; i++) {
            files[i] = new MockMultipartFile(file.getName()+i, "testing"+i+".txt", "text/plain",  Files.readAllBytes(file.toPath()));
        }

        var responseEntity = fileController.uploadFiles(files);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(4, Objects.requireNonNull(responseEntity.getBody()).size());
        assertEquals(92, Objects.requireNonNull(responseEntity.getBody()).stream()
                .map(WordGroupDtoRecord::words)
                .mapToLong(Collection::size)
                .sum());
    }
}