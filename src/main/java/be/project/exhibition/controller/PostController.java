package be.project.exhibition.controller;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.requset.CommentRequest;
import be.project.exhibition.dto.requset.PostCreateRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.repository.UserRepository;
import be.project.exhibition.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping("/{userId}")
    public Response<PostDto> create(@RequestBody PostCreateRequest request, @PathVariable String userId) {
        PostDto postDto = postService.create(request.getTitle(), request.getBody(), userId);
        return Response.success(postDto);
    }

    @PutMapping("/{postId}")
    public Response<PostDto> modify(@RequestBody PostCreateRequest request, @PathVariable Long postId) {
        PostDto postDto = postService.modify(request.getTitle(), request.getBody(), postId);
        return Response.success(postDto);
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@RequestBody PostCreateRequest request, @PathVariable Long postId) {
        postService.delete(postId);
        return Response.success();
    }

    @PostMapping("/{postId}/comment")
    public Response<CommentDto> comment(@RequestBody CommentRequest request, @PathVariable Long postId) {
        CommentDto commentDto = postService.comment(request.getComment(), postId, request.getUserDto());
        return Response.success(commentDto);
    }
}
