package com.counter.words.controller;

import com.counter.words.model.WordGroupDto;
import com.counter.words.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<List<WordGroupDto>> uploadFile(@RequestParam(value = "file", required = false) MultipartFile[] files){
        return ResponseEntity.ok(fileService.parseFile(files));
    }
}
