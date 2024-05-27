package be.project.exhibition.controller;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.requset.ChangePasswordRequest;
import be.project.exhibition.dto.requset.UserJoinRequest;
import be.project.exhibition.dto.requset.UserLoginRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.dto.response.UserInfoResponse;
import be.project.exhibition.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/join")
    public String join(Model model) {
        model.addAttribute("userJoin", new UserJoinRequest());
        return "user/signup";
    }
    @PostMapping("/join")
    public String join(@ModelAttribute UserJoinRequest userJoin) throws IOException {
        userService.join(userJoin.getUserId(), userJoin.getPassword(),
                userJoin.getName(), userJoin.getEmail(), userJoin.getImage());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLogin", new UserLoginRequest());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserLoginRequest request, HttpServletResponse response) {
        userService.login(request.getUserId(), request.getPassword(), response);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Authentication authentication) {
        userService.logout(authentication.getName(), response);
        return "home";
    }

    @GetMapping("/info")
    public String info(Model model, Authentication authentication) {
        UserInfoResponse user = userService.info(authentication.getName());
        model.addAttribute("user", user);
        return "user/info";
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

}
