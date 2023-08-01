package be.project.exhibition.dto.requset;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentRequest {

    private String comment;
    private UserDto userDto;
}
