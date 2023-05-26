package be.project.exhibition.service;

import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.fixture.UserFixture;
import be.project.exhibition.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    void 회원가입시_정상동작() {
        //Given
        String userId = "user1";
        String password = "password";
        String userName = "first";
        String email = "thdwjd@naver.com";

        //When
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn((UserFixture.get(userId, password, userName, email)));
        //Then
        Assertions.assertDoesNotThrow(() -> userService.join(userId,password,userName,email));


    }

    @Test
    void 회원가입시_중복회원_존재() {

    }

    @Test
    void 로그인시_정상동작() {
        //Given

        //When

        //Then

    }

    @Test
    void 로그인시_회원이_없는경우() {
        //Given

        //When

        //Then

    }

    @Test
    void 로그인시_비밀번호가_틀린경우() {
        //Given

        //When

        //Then

    }


}