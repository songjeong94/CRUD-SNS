package be.project.exhibition.service;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.response.UserInfoResponse;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.entity.UserImage;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.repository.UserImageRepository;
import be.project.exhibition.repository.UserRepository;
import be.project.exhibition.utils.FileStore;
import be.project.exhibition.utils.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileStore fileStore;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public void join(String userId, String password, String userName, String email, MultipartFile image) throws IOException {
        // 회원가입 여부 체크
        userRepository.findByUserId(userId).ifPresent(it -> {
                throw new ApplicationException(ErrorCode.DUPLICATED_USER_ID, String.format("%s is duplicated", userName));});
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity userEntity = userRepository.save(UserEntity.of(userId, encodedPassword, userName, email));
        if(image.isEmpty()) {
            userImageRepository.save(new UserImage().builder()
                            .attachImage("default.png")
                            .storedImage("default.png")
                            .user(userEntity)
                            .build());
        } else {
            userImageRepository.save(fileStore.userStoreFile(image, userEntity));
        }

    }

    public void login(String userId, String password, HttpServletResponse response) {
        UserEntity user = getUserEntityOrException(userId);

        // 비밀번호 체크
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALIDED_PASSWORD);
        }
        String token = JwtTokenUtils.generateToken(userId, secretKey, expiredTimeMs);

        Cookie cookie = new Cookie("AUTHORIZATION", token);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        cookie.setHttpOnly(true);
//        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void logout(String userId, HttpServletResponse response) {
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s user is not founded", userId)));
        Cookie cookie = new Cookie("AUTHORIZATION", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public UserInfoResponse info(String userId) {
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s user is not founded", userId)));
        return UserInfoResponse.fromEntity(user);
    }



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
