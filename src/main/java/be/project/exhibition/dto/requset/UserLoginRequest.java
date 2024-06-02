package be.project.exhibition.dto.requset;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
