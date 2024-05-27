package be.project.exhibition.dto.requset;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {

    private String comment;
}
