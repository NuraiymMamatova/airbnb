package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.service.S3Service;
import lombok.RequiredArgsConstructor;
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
public class S3ServiceImpl implements S3Service {

    @Autowired
    private S3Client s3;

    @Value("${aws.path}")
    private String BUCKET_PATH;

    @Override
    public Map<String, String> uploadFile(MultipartFile multipartFile) throws IOException {
        String key = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        PutObjectRequest putObjectAclRequest = PutObjectRequest.builder()
                .bucket("airbnb")
                .contentType("jpeg")
                .contentType("png")
                .key(key).build();
        s3.putObject(putObjectAclRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
        return Map.of("link", BUCKET_PATH + key);
    }

    @Override
    public Map<String, String> deleteFile(String fileLink) {
        String key = fileLink.substring(BUCKET_PATH.length());
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket("airbnb")
                .key(key).build();
        s3.deleteObject(deleteObjectRequest);
        return Map.of("message", fileLink + " has been deleted");
    }
}

