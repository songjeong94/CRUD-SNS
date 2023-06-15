package be.project.exhibition.fixture;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;

public class PostFixture {

    public static PostEntity get(String title, String body, UserEntity userEntity) {
        PostEntity result = new PostEntity();
        result.setTitle(title);
        result.setBody(body);
        UserDto.fromEntity(userEntity);
        return result;
    }
}
