package be.project.exhibition.controller;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.requset.CommentRequest;
import be.project.exhibition.dto.requset.PostCreateRequest;
import be.project.exhibition.dto.response.GetPostDto;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostDto> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        PostDto postDto = postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success(postDto);
    }

    @PutMapping("/{postId}")
    public Response<PostDto> modify(@RequestBody PostCreateRequest request, @PathVariable Long postId, Authentication authentication) {
        PostDto postDto = postService.modify(request.getTitle(), request.getBody(), postId, authentication.getName());
        return Response.success(postDto);
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}")
    public Response<GetPostDto> getPost(@PathVariable Long postId) {
        GetPostDto getPostDto = postService.getPost(postId);
        return Response.success(getPostDto);
    }

    @GetMapping("/all")
    public Response<Page<GetPostDto>> getAllPost(Pageable pageable) {
        return Response.success(postService.allPost(pageable).map(GetPostDto::fromDto));
    }

    @GetMapping("/my")
    public Response<Page<GetPostDto>> myPosts(Pageable pageable, Authentication authentication) {
        return Response.success(postService.myPost(authentication.getName(), pageable).map(GetPostDto::fromDto));
    }

}
