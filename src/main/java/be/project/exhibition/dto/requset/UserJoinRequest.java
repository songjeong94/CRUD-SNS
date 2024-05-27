package be.project.exhibition.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class UserJoinRequest {

    private String userId;
    private String password;
    private String name;
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
