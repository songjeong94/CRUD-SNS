package be.project.exhibition.controller;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.requset.CommentRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public Response<CommentDto> comment(@RequestBody CommentRequest request, @PathVariable Long postId) {
        CommentDto commentDto = commentService.comment(request.getComment(), postId, request.getUserDto());
        return Response.success(commentDto);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public Response<Void> comment_delete(@PathVariable Long commentId, Authentication authentication, @PathVariable String postId) {
        commentService.commentDelete(commentId, authentication.getName());
        return Response.success();
    }
}
