package be.project.exhibition.controller;

import be.project.exhibition.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{postId}")
    public void like(@PathVariable Long postId, Authentication authentication) {
        likeService.like(postId, authentication.getName());
    }
}
