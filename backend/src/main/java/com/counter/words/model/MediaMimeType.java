package com.counter.words.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MediaMimeType {
    APPLICATION_PDF("application/pdf", "pdf"),
    TEXT_PLAIN("text/plain", "txt");

    final String mimeType;
    final String fileExtension;
}
