package be.project.exhibition.dto;

import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.PostImage;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private UserDto user;
    private LocalDateTime createdAt;
    private List<String> storeImages;

    public static PostDto fromEntity(PostEntity entity) {
        return new PostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                UserDto.fromEntity(entity.getUser()),
                entity.getCreatedAt(),
                entity.getPostImages().stream().map(PostImage::getStoreFilename).collect(Collectors.toList())
        );
    }
}
