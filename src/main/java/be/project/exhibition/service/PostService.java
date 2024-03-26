package be.project.exhibition.service;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.response.GetPostDto;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto create(String title, String body, String userId) {
        UserEntity userEntity = getUserEntityOrException(userId);
        PostEntity postEntity = postRepository.save(PostEntity.of(title, body, userEntity));
        return PostDto.fromEntity(postEntity);
    }

    @Transactional
    public PostDto modify(String title, String body, Long postId, String name) {
        PostEntity postEntity = getPostEntityOrException(postId);
        if(!postEntity.getUser().getUserId().equals(name)) {
            throw new ApplicationException(ErrorCode.INVALIDED_PERMISSION);
        }
        postEntity.setTitle(title);
        postEntity.setBody(body);

        return PostDto.fromEntity(postRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(Long postId, String name) {
        PostEntity postEntity = getPostEntityOrException(postId);
        if(!postEntity.getUser().getUserId().equals(name)) {
            throw new ApplicationException(ErrorCode.INVALIDED_PERMISSION);
        }
        postRepository.delete(postEntity);
    }

    public GetPostDto getPost(Long postId) {
        PostEntity postEntity = getPostEntityOrException(postId);
        return GetPostDto.fromEntity(postEntity);
    }

    public Page<PostDto> allPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::fromEntity);
    }

    public Page<PostDto> myPost(String userId, Pageable pageable) {
        UserEntity userEntity = getUserEntityOrException(userId);
        return postRepository.findAllByUser(userEntity, pageable).map(PostDto::fromEntity);
    }

    public UserEntity getUserEntityOrException(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new ApplicationException((ErrorCode.USER_NOT_FOUND),String.format("%s is not founded", userId)));
    }

    public PostEntity getPostEntityOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ApplicationException((ErrorCode.POST_NOT_FOUNDED), "post is not founded"));
    }
    
}
