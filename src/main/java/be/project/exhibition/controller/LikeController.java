package be.project.exhibition.controller;

import be.project.exhibition.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public void like(@PathVariable Long postId, Authentication authentication) {
        likeService.like(postId, authentication.getName());
    }
}
