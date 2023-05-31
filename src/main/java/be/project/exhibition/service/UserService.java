package be.project.exhibition.service;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
 todo : jwt 사용하기
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto join(String userId, String password, String userName, String email) {
        // 회원가입 여부 체크
        userRepository.findById(userId).ifPresent(it -> {
                throw new ApplicationException(ErrorCode.DUPLICATED_USER_ID, String.format("%s is duplicated", userName));});

        UserEntity userEntity = userRepository.save(UserEntity.of(userId, password, userName, email));
        return UserDto.fromEntity(userEntity);
    }

    public UserDto login(String userId, String password) {

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s user is not founded", userId)));

        // 비밀번호 체크
        if (!user.getPassword().equals(password)) {
            throw new ApplicationException(ErrorCode.INVALIDED_PASSWORD);
        } else {
            return UserDto.fromEntity(user);
        }
    }

}
