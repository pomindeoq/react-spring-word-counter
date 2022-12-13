package com.counter.words.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MediaMimeType {
    TEXT_PLAIN("text/plain", "txt");

    final String mimeType;
    final String fileExtension;
}