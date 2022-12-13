package com.counter.words.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WordGroup {
    AG("[A-G]","abcdefg"),
    HN("[H-N]","hijklmn"),
    OU("[O-U]","opqrstu"),
    VZ("[V-Z]","vwxyz");

    private final String name;
    private final String lettersString;
}
