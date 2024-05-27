package be.project.exhibition.dto.response;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponseDTO {

    private Long id;
    private String title;
    private String body;
    private String userId;
    private LocalDateTime createdAt;
    private List<String> images;

    public static PostResponseDTO fromEntity(PostEntity entity) {
        return new PostResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getUser().getUserId(),
                entity.getCreatedAt(),
                entity.getPostImages().stream().map(PostImage::getStoreFilename).collect(Collectors.toList())
                );
    }

    public static PostResponseDTO fromDto(PostDto postDto) {
        return new PostResponseDTO(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getBody(),
                postDto.getUser().getUserId(),
                postDto.getCreatedAt(),
                postDto.getStoreImages()
        );
    }

}
