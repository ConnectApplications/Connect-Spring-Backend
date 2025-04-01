package com.connectbundle.connect.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private final S3Client s3Client;

    @Value("${aws.bucket-name}")
    String bucketName;

    @Value("${aws.access-key}")
    String accessID;

    @Value("${aws.secret-access-key}")
    String secretAccessID;

    @Value("${aws.region:eu-north-1}")
    String region;

    public S3Service(
            @Value("${aws.access-key}") String accessID,
            @Value("${aws.secret-access-key}") String secretAccessID,
            @Value("${aws.region:eu-north-1}") String regionStr) {
        try {
            Region awsRegion = Region.of(regionStr);
            this.s3Client = S3Client.builder()
                    .region(awsRegion)
                    .credentialsProvider(
                            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessID, secretAccessID)))
                    .build();
            logger.info("S3 client initialized successfully for region: {}", regionStr);
        } catch (Exception e) {
            logger.error("Failed to initialize S3 client: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize S3 client", e);
        }
    }



    public String uploadFile(MultipartFile file, String type) {
        if (file == null || file.isEmpty()) {
            logger.error("Cannot upload null or empty file");
            throw new IllegalArgumentException("File cannot be null or empty");
        }
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "unknown";
        }
        
        String uniqueFileName = type + UUID.randomUUID() + "_" + originalFilename;
        logger.info("Attempting to upload file: {} to S3 bucket: {}", uniqueFileName, bucketName);
        
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();
                    
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
                    
            logger.info("Successfully uploaded file: {} to S3", uniqueFileName);
            return uniqueFileName;
        } catch (S3Exception e) {
            logger.error("AWS S3 error uploading file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to upload file to S3: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("IO error reading file for upload: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to read file for upload: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error uploading file: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error uploading file: " + e.getMessage(), e);
        }
    }
}
