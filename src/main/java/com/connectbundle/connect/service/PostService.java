package com.connectbundle.connect.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AmazonS3 amazons3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public String upload(MultipartFile file) throws
            AmazonClientException, IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty!");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazons3.putObject(bucketName, fileName, file.getInputStream(), metadata);
        amazons3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        return amazons3.getUrl(bucketName, fileName).toString();
    }

    public Post savePost(Post post) {
//        post.getMediaUrl();
        return postRepository.save(post);
    }

    public void listBuckets(AmazonS3 amazonS3) {
        amazonS3.listBuckets().forEach(bucket ->
                System.out.println("Bucket Name: " + bucket.getName())
        );
    }


}
