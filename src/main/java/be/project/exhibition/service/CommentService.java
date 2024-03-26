package be.project.exhibition.service;

import be.project.exhibition.dto.CommentDto;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDto comment(String comment, Long postId, UserDto userDto) {
        PostEntity postEntity = getPostEntityOrException(postId);
        UserEntity userEntity = userRepository.findById(postEntity.getUser().getUserId()).orElseThrow(()-> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        CommentEntity commentEntity = commentRepository.save(CommentEntity.of(comment,postEntity, userEntity));
        return CommentDto.from(commentEntity);
    }

    @Transactional
    public void commentDelete(Long commentId, String name) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUNDED));
        if(!commentEntity.getUserEntity().getUserId().equals(name)) {
            throw new ApplicationException(ErrorCode.INVALIDED_PERMISSION);
        }
        commentRepository.delete(commentEntity);
    }

    public UserEntity getUserEntityOrException(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new ApplicationException((ErrorCode.USER_NOT_FOUND),String.format("%s is not founded", userId)));
    }

    public PostEntity getPostEntityOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ApplicationException((ErrorCode.POST_NOT_FOUNDED), "post is not founded"));
    }
}
