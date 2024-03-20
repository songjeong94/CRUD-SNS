package be.project.exhibition.controller;

import be.project.exhibition.dto.response.Response;
import be.project.exhibition.entity.FollowEntity;
import be.project.exhibition.service.FollowService;
import be.project.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{friendId}")
    public Response<?> follow(Authentication authentication, @PathVariable("friendId") String friendId) {
        String follow = followService.follow(authentication.getName(), friendId);
        return Response.success();
    }


    @DeleteMapping("/{friendId}")
    public Response<Void> unfollow(Authentication authentication, @PathVariable("friendId") String friendId) {
        followService.unfollow(authentication.getName(), friendId);
        return Response.success();
    }

    @GetMapping("/{searchId}/following")
    public Response<List> getFollowingList(Authentication authentication, @PathVariable("searchId") String searchId) {
        List<String> followingList = followService.followingList(authentication.getName(), searchId);
        return Response.success(followingList);
    }

    @GetMapping("/{searchId}/follower")
    public Response<List> getFollowerList(Authentication authentication, @PathVariable("searchId") String searchId) {
        List<String> followingList = followService.followerList(authentication.getName(), searchId);
        return Response.success(followingList);
    }
}