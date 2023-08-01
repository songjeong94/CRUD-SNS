package be.project.exhibition.dto;

import be.project.exhibition.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private String comment;
    private PostDto postDto;
    private UserDto userDto;

    public static CommentDto of(String comment, PostDto postDto, UserDto userDto) {
        return new CommentDto(comment, postDto, userDto);
    }

    public static CommentDto from(CommentEntity commentEntity) {
        return CommentDto.of(commentEntity.getComment(), PostDto.fromEntity(commentEntity.getPostEntity()), UserDto.fromEntity(commentEntity.getUserEntity()));
    }
}
