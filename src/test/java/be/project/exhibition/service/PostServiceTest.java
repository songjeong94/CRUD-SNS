package be.project.exhibition.service;

import be.project.exhibition.dto.response.PostResponseDTO;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void testAllPost() {
        // 테스트용 데이터 생성
        UserEntity user = UserEntity.of("test", "tesdt", "Test", "test");
        PostEntity postEntity1 = PostEntity.of("Title 1", "Body 1", user);
        PostEntity postEntity2 = PostEntity.of("Title 2", "Body 2", user);
        List<PostEntity> postEntities = Arrays.asList(postEntity1, postEntity2);
        Page<PostEntity> page = new PageImpl<>(postEntities);

        // mock 설정
        when(postRepository.findAll(any(Pageable.class))).thenReturn(page);

        // 테스트 실행
        Page<PostResponseDTO> result = postService.allPost(PageRequest.of(0, 5));

        // 결과 검증
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Title 1", result.getContent().get(0).getTitle());
        assertEquals("Body 2", result.getContent().get(1).getBody());
    }

//    @Test
//    public void testGetPost() {
//        UserEntity user = UserEntity.of("test", "tesdt", "Test", "test");
//        PostEntity postEntity1 = PostEntity.of("Title 1", "Body 1", user);
//        PostEntity postEntity2 = PostEntity.of("Title 2", "Body 2", user);
//
//        PostEntity mockPost = mock(PostEntity.class);
//        when(mockPost.getTitle()).thenReturn("Title 1");
//        when(postRepository.findById(any())).thenReturn(Optional.of(mockPost));
//
//        PostResponseDTO post = postService.getPost(1L);
//        assertEquals("Title 1", post.getTitle());
//    }
}