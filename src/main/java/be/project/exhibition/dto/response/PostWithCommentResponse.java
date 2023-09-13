package be.project.exhibition.dto.response;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.PostDto;
import be.project.exhibition.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostWithCommentResponse {

    private String title;
    private String body;
    private String userId;
    private List<CommentEntity> commentEntities;
    private Integer likes;
    private Integer viewCount;

    public PostWithCommentResponse fromDto(PostDto postDto, CommentDto commentDto) {
        postDto.getTitle(),
        postDto.getBody(),
        postDto.getUser(),
        postDto.getLikes(),
        postDto.getViewCount(),
        commentDto.
    }
}
