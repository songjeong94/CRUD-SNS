package be.project.exhibition.service;

import be.project.exhibition.dto.CommentDto;
import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.CommentEntity;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.CommentRepository;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostDto create(String title, String body, String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()-> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        PostEntity postEntity = postRepository.save(PostEntity.of(title, body, userEntity));
        return PostDto.fromEntity(postEntity);
    }

    public PostDto modify(String title, String body, Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUNDED));
        UserEntity userEntity = userRepository.findById(postEntity.getUser().getUserId()).orElseThrow(()-> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        if(postEntity.getUser().getUserId() != userEntity.getUserId()) {
            throw new ApplicationException(ErrorCode.INVALIDED_PERMISSION);
        }
        postEntity.setTitle(title);
        postEntity.setBody(body);

        return PostDto.fromEntity(postRepository.saveAndFlush(postEntity));
    }

    public void delete(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUNDED));
        UserEntity userEntity = userRepository.findById(postEntity.getUser().getUserId()).orElseThrow(()-> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        if(postEntity.getUser() != userEntity) {
            throw new ApplicationException(ErrorCode.INVALIDED_PERMISSION);
        }
        postRepository.delete(postEntity);
    }

    public CommentDto comment(String comment, Long postId, UserDto userDto) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUNDED));
        UserEntity userEntity = userRepository.findById(postEntity.getUser().getUserId()).orElseThrow(()-> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        CommentEntity commentEntity = commentRepository.save(CommentEntity.of(comment,postEntity, userEntity));
        return CommentDto.from(commentEntity);
    }
    
}
