package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
@CrossOrigin
@Tag(name = "S3Client", description = "S3 upload")
public class S3Api {
    private final S3Service service;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Upload file", description = "Upload photo, video")
    private Map<String, String> uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException {
        return service.uploadFile(file);
    }

        @Operation(summary = "Delete file", description = "Delete file from database")
        @DeleteMapping
        public Map<String, String> deleteFile(@RequestParam String fileLink) {
            return service.deleteFile(fileLink);
        }
    }

