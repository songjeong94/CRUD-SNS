package be.project.exhibition.controller;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.PostImageUploadDTO;
import be.project.exhibition.dto.requset.CommentRequest;
import be.project.exhibition.dto.requset.PostCreateRequest;
import be.project.exhibition.dto.response.PostResponseDTO;
import be.project.exhibition.dto.response.PostWithCommentResponse;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.repository.UserRepository;
import be.project.exhibition.service.PaginationService;
import be.project.exhibition.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final PaginationService paginationService;
    private final UserRepository userRepository;

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("post", new PostCreateRequest());
        return "post/post-form";
    }
    @PostMapping("/form")
    public String create(@Validated @ModelAttribute PostCreateRequest request, Authentication authentication, RedirectAttributes redirectAttributes, BindingResult result) throws IOException {

        if(result.hasErrors()) {
            return "post/post-form";
        }
        Long postId = postService.create(request.getTitle(), request.getBody(), request.getMultipartFileList(), authentication.getName());
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/api/v1/posts/{postId}";
    }

    // todo
    @PutMapping("/{postId}")
    public Response<PostDto> modify(@RequestBody PostCreateRequest request, @PathVariable Long postId, Authentication authentication) {
        PostDto postDto = postService.modify(request.getTitle(), request.getBody(), postId, authentication.getName());
        return Response.success(postDto);
    }

    // todo
    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}")
    public String getPost(Model model, @PathVariable Long postId, Authentication authentication) {
        PostWithCommentResponse post = postService.getPost(postId);
        model.addAttribute("post", post);
        model.addAttribute("likeCount", postService.countLikes(postId));
        model.addAttribute("liked", postService.isLikedByUser(postId,authentication.getName()));
        model.addAttribute("comment", new CommentRequest());
        return "post/post";
    }

    @GetMapping
    public String getAllPost(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        Page<PostResponseDTO> allPost = postService.allPost(pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), allPost.getTotalPages());

        model.addAttribute("posts", allPost);
        model.addAttribute("paginationBarNumbers", barNumbers);

        return "post/post-list";
    }

    @GetMapping("/my")
    public String getAllPost(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
                                 Pageable pageable, Model model, Authentication authentication) {
        Page<PostResponseDTO> myPosts = postService.myPost(authentication.getName(), pageable).map(PostResponseDTO::fromDto);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), myPosts.getTotalPages());

        model.addAttribute("posts", myPosts);
        model.addAttribute("paginationBarNumbers", barNumbers);

        return "post/post-list";
    }

    @PostMapping("/{postId}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likePost(@PathVariable Long postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", postService.countLikes(postId));
        response.put("liked", postService.isLikedByUser(postId, authentication.getName()));
        return ResponseEntity.ok(response);
    }
}
