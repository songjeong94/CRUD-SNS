package be.project.exhibition.controller;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.requset.UserJoinRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.dto.response.UserJoinResponse;
import be.project.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto user = userService.join(userJoinRequest.getUserId(), userJoinRequest.getPassword(),
                            userJoinRequest.getUserName(), userJoinRequest.getEmail());
        return Response.success(UserJoinResponse.fromUserDto(user));
    }
}
