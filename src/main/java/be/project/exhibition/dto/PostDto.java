package be.project.exhibition.dto;

import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private UserDto user;
    private Integer likeCount;

    public static PostDto fromEntity(PostEntity entity) {
        return new PostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                UserDto.fromEntity(entity.getUser()),
                entity.getLikes().size());
    }
}
