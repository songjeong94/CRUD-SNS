package be.project.exhibition.service;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.fixture.UserFixture;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;


    @Test
    void 포스트_작성_성공() {
        PostEntity post = PostEntity.of("title", "body", user);

        //Given
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepository.save(any())).thenReturn(Optional.of(mock(PostEntity.class)));
        //Then
        Assertions.assertDoesNotThrow(() -> postService.create("title", "body", "song2"));
    }
    UserEntity user = UserFixture.get("user1", "user1", "user1", "user1@example.com");
}