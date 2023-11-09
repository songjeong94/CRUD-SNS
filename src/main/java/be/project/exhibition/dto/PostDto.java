package be.project.exhibition.dto;

import be.project.exhibition.entity.LikeEntity;
import be.project.exhibition.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private UserDto user;
    private List<LikeEntity> likes;

    public static PostDto fromEntity(PostEntity entity) {
        return new PostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                UserDto.fromEntity(entity.getUser()),
                entity.getLikesList());
    }
}
