package com.counter.words.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void whenFilesAreEmptyShouldReturnEmptyList() {
        MultipartFile[] files = new MultipartFile[2];
        files[0] = new MockMultipartFile("file1", "file1.txt", "text/plain", "".getBytes());
        files[1] = new MockMultipartFile("file2", "file2.txt", "text/plain", "".getBytes());

        List<MultipartFile> result = FileUtils.filterEmpty(files);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenFilesAreNullShouldReturnEmptyList() {
        MultipartFile[] files = new MultipartFile[0];
        List<MultipartFile> result = FileUtils.filterEmpty(files);
        assertTrue(result.isEmpty());
    }

    @Test
    void whenFilesAreNotEmptyShouldReturnNonemptyList() {
        MultipartFile[] files = new MultipartFile[2];
        files[0] = new MockMultipartFile("file1", "file1.txt", "text/plain", "file1".getBytes());
        files[1] = new MockMultipartFile("file2", "file2.txt", "text/plain", "file2".getBytes());

        List<MultipartFile> result = FileUtils.filterEmpty(files);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void whenInputIsEmptyThenReturnEmptyList() {
        List<MultipartFile> files = new ArrayList<>();
        List<MultipartFile> result = FileUtils.filterWrongType(files);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return correct list when the input contains only correct type files")
    void whenInputContainsOnlyCorrectTypeFilesShouldReturnCorrectList() {
        List<MultipartFile> files = new ArrayList<>();
        files.add(
                new MockMultipartFile("file1", "file1.txt", "text/plain", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file2", "file2.txt", "text/plain", "some text".getBytes()));
        List<MultipartFile> result = FileUtils.filterWrongType(files);
        assertEquals(2, result.size());
    }

    @Test
    void whenInputContainsOnlyWrongTypeFilesShouldReturnEmptyList() {
        List<MultipartFile> files = new ArrayList<>();
        files.add(
                new MockMultipartFile(
                        "file", "file.txt", "application/pdf", "some text".getBytes()));
        files.add(
                new MockMultipartFile(
                        "file", "file.txt", "application/pdf", "some text".getBytes()));
        files.add(
                new MockMultipartFile(
                        "file", "file.txt", "application/pdf", "some text".getBytes()));

        List<MultipartFile> result = FileUtils.filterWrongType(files);

        assertTrue(result.isEmpty());
    }

    @Test
    void wenInputContainsBothCorrectAndWrongTypeFilesShouldReturnCorrectList() {
        List<MultipartFile> files = new ArrayList<>();
        files.add(
                new MockMultipartFile("file1", "file1.txt", "text/plain", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file2", "file2.txt", "text/plain", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file3", "file3.txt", "application/pdf", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file4", "file4.txt", "text/plain", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file5", "file5.txt", "text/plain", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file6", "file6.txt", "application/pdf", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file7", "file7.txt", "text/plain", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file8", "file8.txt", "application/json", "some text".getBytes()));
        files.add(
                new MockMultipartFile("file9", "file9.txt", "application/json", "some text".getBytes()));
        files.add(
                new MockMultipartFile(
                        "file10", "file10.txt", "text/plain", "some text".getBytes()));

        List<MultipartFile> result = FileUtils.filterWrongType(files);

        assertEquals(6, result.size());
    }
}