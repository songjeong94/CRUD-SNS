package be.project.exhibition.dto.requset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class UserJoinRequest {

    @NotBlank(message = "아이디를 입력해 주세요")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 항목 입니다.")
    @Min(value = 8, message = "비밀번호는 최소 8자리 이상입니다.")
    private String password;

    @NotBlank(message = "성함을 입력해 주세요")
    private String name;

    @NotBlank
    @Email
    private String email;

    private MultipartFile image;


    public UserJoinRequest(String userId, String password, String name, String email, MultipartFile image) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public UserJoinRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
