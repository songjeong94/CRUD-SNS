package be.project.exhibition.service;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.UserRepository;
import be.project.exhibition.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public UserDto join(String userId, String password, String userName, String email) {
        // 회원가입 여부 체크
        userRepository.findByUserId(userId).ifPresent(it -> {
                throw new ApplicationException(ErrorCode.DUPLICATED_USER_ID, String.format("%s is duplicated", userName));});
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity userEntity = userRepository.save(UserEntity.of(userId, encodedPassword, userName, email));
        return UserDto.fromEntity(userEntity);
    }

    public String login(String userId, String password) {
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s user is not founded", userId)));

        // 비밀번호 체크
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALIDED_PASSWORD);
        }
        String token = JwtTokenUtils.generateToken(userId, secretKey, expiredTimeMs);

        return token;
    }

    // TODO: logout



    public void changePassword(String userId, String oldPassword, String newPassword, String checkPassword) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s user is not founded", userId)));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALIDED_PASSWORD);
        }

        if(!newPassword.equals(checkPassword)) {
            throw new ApplicationException(ErrorCode.INVALIDED_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserDto loadUserByUserName(String userId) {
        return userRepository.findByUserId(userId).map(UserDto::fromEntity).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", userId)));
    }

    public UserEntity getUserEntityOrException(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new ApplicationException((ErrorCode.USER_NOT_FOUND),String.format("%s is not founded", userId)));
    }

}
