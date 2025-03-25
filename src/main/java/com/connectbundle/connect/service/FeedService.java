package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.PostAuthorDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.repository.FeedRepository;
import com.connectbundle.connect.repository.LikeRepository;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getAllPostsInReverseOrder() {
        List<Post> feed = feedRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDTO> postResponseDTOS = feed.stream()
                .map(post -> {
                    PostResponseDTO postDTO = modelMapper.map(post, PostResponseDTO.class);

                    PostAuthorDTO authorDTO = modelMapper.map(post.getAuthor(), PostAuthorDTO.class);

                    postDTO.setAuthor(authorDTO);
//                    postDTO.setIsLikedByUser(likeRepository.existsByPostIdAndUserId(post.getId(), userId));
//                    postDTO.setIsBookmarked(bookmarkRepository.existsByPostIdAndUserId(post.getId(), userId));

                    return postDTO;
                })
                .toList();
        return BaseResponse.success(postResponseDTOS,"Feed fetched successfully",postResponseDTOS.size());
    }
}
