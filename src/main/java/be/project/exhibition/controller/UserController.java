package be.project.exhibition.controller;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.requset.ChangePasswordRequest;
import be.project.exhibition.dto.requset.UserJoinRequest;
import be.project.exhibition.dto.requset.UserLoginRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.dto.response.UserJoinResponse;
import be.project.exhibition.dto.response.UserLoginResponse;
import be.project.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserDto user = userService.join(request.getUserId(), request.getPassword(),
                            request.getName(), request.getEmail());
        return Response.success(UserJoinResponse.fromUserDto(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserId(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @PatchMapping("/change-password")
    public Response<String> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        userService.changePassword(authentication.getName(),
                request.getOldPassword(),
                request.getNewPassword(),
                request.getCheckPassword());
        return Response.success("password change success");
    }

    // TODO: change userInfo

    // TODO: GET userInfo { name, email, followers&count, following&count, phoneNumber }
}
