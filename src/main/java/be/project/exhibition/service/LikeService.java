package be.project.exhibition.service;

import be.project.exhibition.entity.LikeEntity;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.LikeRepository;
import be.project.exhibition.repository.PostRepository;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void like (Long postId, String userName) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUNDED));
        UserEntity userEntity = userRepository.findByUserId(userName).orElseThrow(() -> new ApplicationException((ErrorCode.USER_NOT_FOUND), String.format("%s is not founded", userName)));
        LikeEntity likeEntity = likeRepository.existsByUserEntityAndPostEntity(userEntity, postEntity).orElseThrow(() -> new ApplicationException(ErrorCode.ALREADY_LIKE));
        likeRepository.save(likeEntity);
    }
}
