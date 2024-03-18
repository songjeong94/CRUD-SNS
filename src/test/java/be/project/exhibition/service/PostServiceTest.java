package be.project.exhibition.service;

import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

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