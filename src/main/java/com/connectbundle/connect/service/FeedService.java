package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.PostAuthorDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.repository.FeedRepository;
import com.connectbundle.connect.repository.LikeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${s3_public_url_prefix}")
    private String S3_PUBLIC_URL_PREFIX;

    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getAllPostsInReverseOrder() {
        List<Post> feed = feedRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDTO> postResponseDTOS = feed.stream()
                .map(this::mapPostToResponseDTO)
                .toList();
        return BaseResponse.success(postResponseDTOS,"Feed fetched successfully",postResponseDTOS.size());
    }
    private PostResponseDTO mapPostToResponseDTO(Post post) {
        PostResponseDTO dto = modelMapper.map(post, PostResponseDTO.class);
        PostAuthorDTO authorDTO = modelMapper.map(post.getAuthor(), PostAuthorDTO.class);
        dto.setAuthor(authorDTO);

        if (post.getMedia() != null && !post.getMedia().isBlank()) {
            dto.setMedia(S3_PUBLIC_URL_PREFIX + post.getMedia());
        }

        return dto;
    }
}
