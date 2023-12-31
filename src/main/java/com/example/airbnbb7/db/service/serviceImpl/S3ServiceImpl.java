package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    @Autowired
    private S3Client s3;

    @Value("${aws.bucket.url}")
    private String BUCKET_PATH;

    @Value("${aws.bucket.name}")
    private String NAME_PATH;

    @Override
    public Map<String, String> uploadFile(MultipartFile multipartFile) throws IOException {
        String key = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        PutObjectRequest putObjectAclRequest = PutObjectRequest.builder()
                .bucket(NAME_PATH)
                .contentType("jpeg")
                .contentType("png")
                .key(key).build();
        s3.putObject(putObjectAclRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
        log.info("add file {} to server", key);
        return Map.of("link", BUCKET_PATH + key);
    }

    @Override
    public Map<String, String> deleteFile(String fileLink) {
        String key = fileLink.substring(BUCKET_PATH.length());
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(NAME_PATH)
                .key(key).build();
        s3.deleteObject(deleteObjectRequest);
        log.info("delete file {}", key);
        return Map.of("message", fileLink + " has been deleted");
    }
}

