package be.project.exhibition.dto.response;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.entity.LikeEntity;
import be.project.exhibition.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPostDto {

    private String title;
    private String body;
    private String userId;
    private List<LikeEntity> likes;


    public static GetPostDto fromEntity(PostEntity entity) {
        return new GetPostDto(
                entity.getTitle(),
                entity.getBody(),
                entity.getUser().getUserId(),
                entity.getLikesList()
        );
    }

    public static GetPostDto fromDto(PostDto postDto) {
        return new GetPostDto(
                postDto.getTitle(),
                postDto.getBody(),
                postDto.getUser().getUserId(),
                postDto.getLikes()
        );
    }

}
