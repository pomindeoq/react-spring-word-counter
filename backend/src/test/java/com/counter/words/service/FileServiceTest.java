package com.counter.words.service;

import com.counter.words.exception.GeneralException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private MultipartFile file;

    @InjectMocks
    private FileService fileService;

    @Test
    void whenFilesIsNullShouldReturnEmptyList() {
        MultipartFile[] files = null;
        assertThrows(GeneralException.class, () -> fileService.parseFile(files));
    }

    @Test
    void whenFilesIsEmptyShouldThrowException() {
        MultipartFile[] files = new MultipartFile[0];
        assertThrows(GeneralException.class, () -> fileService.parseFile(files));
    }

    @Test
    void whenFilesContainsEmptyFileShouldThrowException() {
        MultipartFile[] files = {file};
        when(file.isEmpty()).thenReturn(true);
        assertThrows(GeneralException.class, () -> fileService.parseFile(files));
    }

    @Test
    void whenFilesContainsWrongTypeFileShouldThrowException() {
        MultipartFile[] files = {file};
        when(file.getContentType()).thenReturn("application/pdf");
        assertThrows(GeneralException.class, () -> fileService.parseFile(files));
    }
}