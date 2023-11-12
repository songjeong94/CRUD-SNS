package be.project.exhibition.service;

import be.project.exhibition.entity.FollowEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.FollowRepository;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(String toUserId, String fromUserId) {
        UserEntity user = userRepository.findById(fromUserId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s user is not founded", fromUserId)));

        FollowEntity follow = FollowEntity.of(toUserId, user);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(String toUserId, String fromUserId) {
        followRepository.deleteByToUserIdAndFromUserId(toUserId, fromUserId);
    }
}
