package be.project.exhibition.dto.response;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.CommentEntity;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostWithCommentResponse {

    private Long id;
    private String title;
    private String body;
    private UserDto user;
    private LocalDateTime createdAt;
    private List<String> storeImages;
    private List<CommentEntity> comments;

    public static PostWithCommentResponse fromEntity(PostEntity post) {
        return new PostWithCommentResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserDto.fromEntity(post.getUser()),
                post.getCreatedAt(),
                post.getPostImages().stream().map(PostImage::getStoreFilename).collect(Collectors.toList()),
                post.getComments()
        );
    }

}
