package com.counter.words.controller;

import com.counter.words.WordsApplicationTests;
import com.counter.words.exception.GeneralException;
import com.counter.words.exception.constants.ErrorCodes;
import com.counter.words.model.WordCount;
import com.counter.words.model.WordGroup;
import com.counter.words.model.WordGroupDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FileControllerTest extends WordsApplicationTests {

    @Autowired
    FileController fileController;

    @Test
    void filesMissingShouldThrowException() {
        MultipartFile[] files = null;

        GeneralException actualException = assertThrows(GeneralException.class, () -> {
            fileController.uploadFile(files);
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

        GeneralException actualException = assertThrows(GeneralException.class, () -> {
            fileController.uploadFile(files);
        });

        GeneralException expectedException = ErrorCodes.FILES_EMPTY.getException();
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

        GeneralException actualException = assertThrows(GeneralException.class, () -> {
            fileController.uploadFile(files);
        });

        GeneralException expectedException = ErrorCodes.FILES_TYPE_NOT_VALID.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
    }

    @Test
    void uploadFileShouldReturnCorrectListOfWordGroups() {
        MultipartFile[] files =
                new MultipartFile[]{
                        new MockMultipartFile("file", "file.txt", "text/plain", "test".getBytes())
                };
        List<WordGroupDto> wordGroupDtos = List.of(WordGroupDto.builder()
                .name(WordGroup.OU.getName())
                .words(List.of(WordCount.builder()
                        .word("test")
                        .count(1L)
                        .build()))
                .build());

        ResponseEntity<List<WordGroupDto>> responseEntity = fileController.uploadFile(files);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wordGroupDtos, responseEntity.getBody());
    }
}