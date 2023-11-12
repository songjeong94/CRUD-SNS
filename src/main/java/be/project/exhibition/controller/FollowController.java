package be.project.exhibition.controller;

import be.project.exhibition.dto.response.Response;
import be.project.exhibition.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userName}")
    public Response<Void> follow(@PathVariable String userName, Authentication authentication) {
        followService.follow(userName, authentication.getName());
        return Response.success();
    }

    @DeleteMapping("/{userName}")
    public Response<Void> unfollow(@PathVariable String userName, Authentication authentication) {
        followService.unfollow(userName, authentication.getName());
        return Response.success();
    }
}
