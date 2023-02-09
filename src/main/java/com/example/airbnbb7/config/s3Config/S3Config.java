package com.example.airbnbb7.config.s3Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
@Setter
public class S3Config {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    @Bean
    S3Client s3Client() {
        Region region = Region.EU_CENTRAL_1;
        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials
                                .create(accessKey, secretKey))).build();

    }
}
