package com.example.airbnbb7.db.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface S3Service {
    Map<String, String> uploadFile(MultipartFile multipartFile) throws IOException;

    Map<String, String> deleteFile(String fileLink);
}
