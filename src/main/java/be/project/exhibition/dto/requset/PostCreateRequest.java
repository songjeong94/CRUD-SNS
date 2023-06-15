package be.project.exhibition.dto.requset;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostCreateRequest {
    String title;
    String body;
}
