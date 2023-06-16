package be.project.exhibition.service;

import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.fixture.UserFixture;
import be.project.exhibition.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    void 회원가입시_정상동작() {
        //Given
        String userId = "user1";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        String userName = "first";
        String email = "thdwjd@naver.com";

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn((UserFixture.get(userId, encodedPassword, userName, email)));

        //Then
        assertDoesNotThrow(() -> userService.join(userId,encodedPassword,userName,email));
    }

    @Test
    void 회원가입시_중복회원_존재() {
        //Given
        String userId = "user1";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        String userName = "first";
        String email = "thdwjd@naver.com";

        UserEntity fixture = UserFixture.get(userId, password, userName, email);

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.of(Mockito.mock(UserEntity.class)));
        when(userRepository.save(any())).thenReturn(Optional.of(fixture));

        //Then
        ApplicationException e = assertThrows(ApplicationException.class, () -> userService.join(userId, encodedPassword, userName, email));
        assertEquals(ErrorCode.DUPLICATED_USER_ID, e.getErrorCode());
    }

    @Test
    void 로그인시_정상동작() {
        //Given
        String userId = "user1";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        String userName = "first";
        String email = "thdwjd@naver.com";

        UserEntity entity = UserFixture.get(userId,encodedPassword,userName,email);

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        when(userRepository.save(any())).thenReturn(Optional.of(entity));
        //Then
        assertDoesNotThrow(() -> userService.login("user1", "password"));
    }

    @Test
    void 로그인시_회원이_없는경우() {
        ApplicationException e = assertThrows(ApplicationException.class, () -> userService.login("ser", "pas"));
        assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 로그인시_비밀번호가_틀린경우() {
        //Given
        String userId = "user1";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        String userName = "first";
        String email = "thdwjd@naver.com";

        UserEntity user = UserFixture.get(userId, encodedPassword, userName, email);

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(Optional.of(user));
        //Then
        ApplicationException e = assertThrows(ApplicationException.class, () -> userService.login("user1", "pas"));
        assertEquals(ErrorCode.INVALIDED_PASSWORD, e.getErrorCode());

    }


}