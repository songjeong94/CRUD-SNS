package be.project.exhibition.controller;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.requset.UserJoinRequest;
import be.project.exhibition.dto.requset.UserLoginRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.dto.response.UserJoinResponse;
import be.project.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserDto user = userService.join(request.getUserId(), request.getPassword(),
                            request.getUserName(), request.getEmail());
        return Response.success(UserJoinResponse.fromUserDto(user));
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserId(), request.getPassword());
        return Response.success(token);
    }
}
