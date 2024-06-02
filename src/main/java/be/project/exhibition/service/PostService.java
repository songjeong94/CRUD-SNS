package be.project.exhibition.service;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.response.PostResponseDTO;
import be.project.exhibition.dto.response.PostWithCommentResponse;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.PostImage;
import be.project.exhibition.entity.PostLikeEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.PostImageRepository;
import be.project.exhibition.repository.PostLikeRepository;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import be.project.exhibition.utils.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;
    private final FileStore fileStore;
//    private final RedisTemplate<String, PostDto> redisTemplate;

    @Transactional
    public Long create(String title, String body, List<MultipartFile> images, String userId) throws IOException {
        UserEntity userEntity = getUserEntityOrException(userId);
        PostEntity postEntity = postRepository.save(PostEntity.of(title, body, userEntity));

        List<PostImage> storeImageFiles = fileStore.postStoreFiles(images, postEntity);
        for (PostImage entity : storeImageFiles) {
            postImageRepository.save(entity);
        }
        return postEntity.getId();
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

    public PostWithCommentResponse getPost(Long postId) {
        PostEntity postEntity = getPostEntityOrException(postId);
        return PostWithCommentResponse.fromEntity(postEntity);
    }

    public Page<PostResponseDTO> allPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponseDTO::fromEntity);
    }

    public Page<PostDto> myPost(String userId, Pageable pageable) {
        UserEntity userEntity = getUserEntityOrException(userId);
        return postRepository.findAllByUser(userEntity, pageable).map(PostDto::fromEntity);
    }

    @Transactional
    public void like(Long postId, String userId) {

        Optional<PostLikeEntity> likeEntity = postLikeRepository.findByPostIdAndAndUserUserId(postId, userId);

        if(likeEntity.isPresent()) {
            postLikeRepository.delete(likeEntity.get());
        } else {

            PostEntity postEntity = getPostEntityOrException(postId);
            UserEntity userEntity = getUserEntityOrException(userId);

            PostLikeEntity like = PostLikeEntity.builder()
                    .post(postEntity)
                    .user(userEntity)
                    .build();

            postLikeRepository.save(like);
        }
    }

    public boolean isLikedByUser(Long postId, String userId) {
        return postLikeRepository.findByPostIdAndAndUserUserId(postId, userId).isPresent();
    }

    public long countLikes(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public UserEntity getUserEntityOrException(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new ApplicationException((ErrorCode.USER_NOT_FOUND),String.format("%s is not founded", userId)));
    }

    public PostEntity getPostEntityOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ApplicationException((ErrorCode.POST_NOT_FOUNDED), "post is not founded"));
    }
    
}
