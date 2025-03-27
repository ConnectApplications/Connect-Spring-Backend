package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connection")
public class FollowController {

    @Autowired
    private FollowService followService;


//    @PostMapping("/{ollow")
//    public ResponseEntity<BaseResponse<String>> followUser(
//            @PathVariable Long followingId,
//            @AuthenticationPrincipal User authenticatedUser) {
//
//        Long followerId = authenticatedUser.getId(); // Extract from authenticated user
//        return BaseResponse.successMessage(followService.followUser(followerId, followingId));
//    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<User>> getAuthenticatedUser(@AuthenticationPrincipal User authenticatedUser) {
        if (authenticatedUser == null) {
            return BaseResponse.error("No authenticated user found", HttpStatus.UNAUTHORIZED);
        }
        return BaseResponse.success(authenticatedUser, "Authenticated user details", 1);
    }


//    @DeleteMapping("/unfollow")
//    public ResponseEntity<String> unfollowUser(@RequestParam Long followerId, @RequestParam Long followingId) {
//        return ResponseEntity.ok(followService.unfollowUser(followerId, followingId));
//    }
//
//    @GetMapping("/followers/{userId}")
//    public ResponseEntity<List<Follow>> getFollowers(@PathVariable Long userId) {
//        return ResponseEntity.ok(followService.getFollowers(userId));
//    }
//
//    @GetMapping("/following/{userId}")
//    public ResponseEntity<List<Follow>> getFollowing(@PathVariable Long userId) {
//        return ResponseEntity.ok(followService.getFollowing(userId));
//    }
}
