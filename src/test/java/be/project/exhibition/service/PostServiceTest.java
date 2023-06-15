package be.project.exhibition.service;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.fixture.PostFixture;
import be.project.exhibition.fixture.UserFixture;
import be.project.exhibition.repository.PostRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Disabled
    @Test
    void 포스트_작성_성공() {
        //Given
        String title = "title";
        String body = "body";
        UserEntity user = user1();

        PostEntity postEntity = PostFixture.get(title, body, user);

        //When
        when(postRepository.findById(postEntity.getId())).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(PostFixture.get(title, body, user));

        //Then

    }

    @Test
    void 포스트_수정() {
        //Given

        //When

        //Then
    }

    @Test
    void 포스트_삭제() {
        //Given

        //When

        //Then
    }

    static UserEntity user1() {
        String userId = "user1";
        String password = "password";
        String userName = "first";
        String email = "thdwjd1@naver.com";

        UserEntity user = UserFixture.get(userId, password, userName, email);
        return user;
    }

    static UserEntity user2() {
        String userId = "user2";
        String password = "password";
        String userName = "second";
        String email = "thdwjd2@naver.com";

        UserEntity user = UserFixture.get(userId, password, userName, email);
        return user;
    }


}