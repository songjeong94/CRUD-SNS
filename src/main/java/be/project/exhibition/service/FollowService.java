package be.project.exhibition.service;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.FollowEntity;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.FollowRepository;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public String follow(String fromUser, String toUser) {

        if(fromUser.equals(toUser)) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, "본인을 팔로우 할 수 없습니다.");
        }
        UserEntity fromUserEntity = getUserEntityOrException(fromUser);
        UserEntity toUserEntity = getUserEntityOrException(toUser);

        if(followRepository.findFollow(fromUserEntity, toUserEntity).isPresent())
            throw new ApplicationException(ErrorCode.BAD_REQUEST, "이미 팔로우 되었습니다.");

        FollowEntity follow = FollowEntity.builder()
                .toUser(toUserEntity)
                .fromUser(fromUserEntity)
                .build();
        followRepository.save(follow);
        return "Success";
    }

    @Transactional
    public void unfollow(String fromUser, String toUser) {
        UserEntity fromUserEntity = getUserEntityOrException(fromUser);
        followRepository.deleteFollowEntityByFromUser(fromUserEntity);
    }

    public List<String> followingList(String requestUser, String selectedUser) {
        // 요청 유저
        UserEntity reqeustUserEntity = getUserEntityOrException(requestUser);
        // 선택 유저
        UserEntity selectedUserEntity = getUserEntityOrException(selectedUser);
        List<FollowEntity> byFromUser = followRepository.findAllByFromUser(selectedUserEntity);
        List<String> followList = new ArrayList<>();
        for (FollowEntity f : byFromUser) {
            followList.add(f.getToUser().getName());
        }
        return followList;
    }

    public List<String> followerList(String requestUser, String selectedUser) {
        UserEntity reqeustUserEntity = getUserEntityOrException(requestUser);
        UserEntity selectedUserEntity = getUserEntityOrException(selectedUser);
        List<FollowEntity> requestUer = followRepository.findAllByToUser(selectedUserEntity);
        List<String> followList = new ArrayList<>();
        for (FollowEntity f : requestUer) {
            followList.add(f.getFromUser().getName());
        }
        return followList;
    }

    public UserEntity getUserEntityOrException(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new ApplicationException((ErrorCode.USER_NOT_FOUND),String.format("%s is not founded", userId)));
    }

}
