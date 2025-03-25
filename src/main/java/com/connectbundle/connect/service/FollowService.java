package com.connectbundle.connect.service;

import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Follow;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.FollowRepository;
import com.connectbundle.connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public String followUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User following = userRepository.findById(followingId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            return "You are already following this user.";
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);

        return "Followed successfully!";
    }
}
