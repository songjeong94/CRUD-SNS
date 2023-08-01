package be.project.exhibition.service;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.fixture.PostFixture;
import be.project.exhibition.fixture.UserFixture;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Test
    void 포스트_작성_성공() {
        //Given
        String title = "title";
        String body = "body";
        String userName = "test01";

        //When
        when(userRepository.findByUserId(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        //Then
        Assertions.assertDoesNotThrow(() -> postService.create(title, body, userName));
    }
}