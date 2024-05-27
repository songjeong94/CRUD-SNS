package be.project.exhibition.controller;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.requset.CommentRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public String comment(@ModelAttribute CommentRequest comment, @PathVariable Long postId, Authentication authentication, RedirectAttributes redirectAttributes) {
        commentService.comment(postId, comment.getComment(), authentication.getName());
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/api/v1/posts/{postId}";
    }

    @DeleteMapping("/{postId}/{commentId}")
    public Response<Void> comment_delete(@PathVariable Long commentId, Authentication authentication, @PathVariable String postId) {
        commentService.commentDelete(commentId, authentication.getName());
        return Response.success();
    }
}
