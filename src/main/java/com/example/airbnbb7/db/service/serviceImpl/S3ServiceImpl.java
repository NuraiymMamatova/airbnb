package com.example.airbnbb7.db.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;

@Service
public class S3ServiceImpl {

    @Autowired
    private S3Client s3;

    @Value("${aws.path}")
    private String BUCKET_PATH;

    public Map<String,String> uploadFile(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest putObjectAclRequest = PutObjectRequest.builder()
                        .bucket("airbnb")
                .contentType("jpeg")
                .contentType("png")
                                .key(key).build();
        s3.putObject(putObjectAclRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    return Map.of("link",BUCKET_PATH+key);
    }
}
