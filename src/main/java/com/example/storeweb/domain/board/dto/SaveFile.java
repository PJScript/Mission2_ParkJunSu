package com.example.storeweb.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class SaveFile {
    String originalFileName;
    String savedFilename;
    String savedFilePath;
    boolean isThumbnail;
}
