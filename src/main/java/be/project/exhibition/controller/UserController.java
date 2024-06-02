package be.project.exhibition.controller;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.dto.requset.ChangePasswordRequest;
import be.project.exhibition.dto.requset.UserJoinRequest;
import be.project.exhibition.dto.requset.UserLoginRequest;
import be.project.exhibition.dto.response.Response;
import be.project.exhibition.dto.response.UserInfoResponse;
import be.project.exhibition.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String join(@Validated @ModelAttribute("userJoin") UserJoinRequest userJoin, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            return "user/signup";
        }
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
    public String login(@Validated @ModelAttribute("userLogin") UserLoginRequest userLogin, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "user/login";
        }
        userService.login(userLogin.getUserId(), userLogin.getPassword(), response);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Authentication authentication) {
        userService.logout(authentication.getName(), response);
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(Model model, Authentication authentication) {
        UserInfoResponse user = userService.info(authentication.getName());
        model.addAttribute("user", user);
        return "user/info";
    }

    @GetMapping("/password")
    public String changePassword(Model model) {
        model.addAttribute("password", new ChangePasswordRequest());
        return "user/password";
    }

    @PostMapping("/password")
    public String changePassword(@ModelAttribute("password") ChangePasswordRequest password, Authentication authentication) {
        userService.changePassword(authentication.getName(),
                password.getOldPassword(),
                password.getNewPassword(),
                password.getCheckPassword());
        return "redirect:/";
    }

}
