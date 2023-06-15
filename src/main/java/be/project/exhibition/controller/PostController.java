package be.project.exhibition.controller;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.requset.PostCreateRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.repository.UserRepository;
import be.project.exhibition.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/post")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping("/{userId}")
    public Response<Void> create(PostCreateRequest request, @PathVariable String userId) {
        postService.create(request.getTitle(), request.getBody(), userId);
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostDto> modify(PostCreateRequest request, @PathVariable Long postId) {
        PostDto postDto = postService.modify(request.getTitle(), request.getBody(), postId);
        return Response.success(postDto);
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(PostCreateRequest request, @PathVariable Long postId) {
        postService.delete(postId);
        return Response.success();
    }

}
