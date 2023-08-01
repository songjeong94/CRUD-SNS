package be.project.exhibition.controller;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.requset.UserJoinRequest;
import be.project.exhibition.dto.requset.UserLoginRequest;
import be.project.exhibition.exception.ApplicationException;
import be.project.exhibition.exception.ErrorCode;
import be.project.exhibition.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 회원가입() throws Exception {
        //Given
        String userId = "userId";
        String userName = "userName";
        String password = "password";
        String email = "thdwjd@naver.com";

        //When
        when(userService.join(userId, userName, password, email)).thenReturn(mock(UserDto.class));
        //Then
        mockMvc.perform(post("/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userId, userName, password, email)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입할때_이미_아이디가_존재하는경우() throws Exception {
        //Given
        String userId = "userId";
        String userName = "userName";
        String password = "password";
        String email = "thdwjd@naver.com";

        //When
        when(userService.join(userId, password, userName, email)).thenThrow(new ApplicationException(ErrorCode.DUPLICATED_USER_ID));
        //Then
        mockMvc.perform(post("/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userId, password, userName, email)))
                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Disabled
    @Test
    public void 로그인_성공() throws Exception{
        //Given
        String userId = "userId";
        String password = "password";

        //When
//        when(userService.login(userId, password)).thenReturn(mock(UserDto.class));
        //Then
        mockMvc.perform(post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userId, password)))
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인시_유저가_존재하지않을때() throws Exception{
        //Given
        String userId = "userId";
        String password = "password";

        //When
        when(userService.login(userId,password)).thenThrow(new ApplicationException(ErrorCode.USER_NOT_FOUND));
        //Then
        mockMvc.perform(post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserLoginRequest(userId, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인시_비밀번호가_틀렸을때() throws Exception{
        //Given
        String userId = "userId";
        String password = "password";

        //When
        when(userService.login(userId,password)).thenThrow(new ApplicationException(ErrorCode.INVALIDED_PASSWORD));

        //Then
        mockMvc.perform(post("/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserLoginRequest(userId, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
