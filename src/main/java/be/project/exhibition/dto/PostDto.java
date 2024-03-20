package be.project.exhibition.dto;

import be.project.exhibition.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private UserDto user;

    public static PostDto fromEntity(PostEntity entity) {
        return new PostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                UserDto.fromEntity(entity.getUser())
        );
    }
}
