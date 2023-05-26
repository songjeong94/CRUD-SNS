package be.project.exhibition.service;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto join(String userId, String password, String userName, String email) {
        userRepository.findByUserName(userName).ifPresent( er -> { throw new RuntimeException("중복된 아이디 입니다."); });
        UserEntity userEntity = userRepository.save(UserEntity.of(userId, password, userName, email));
        return UserDto.fromEntity(userEntity);
    }

    public UserDto login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException());

        // 비밀번호 체크
        if(!user.getPassword().equals(password)) {
            throw new RuntimeException();
        }

        return new UserDto();
    }

}
