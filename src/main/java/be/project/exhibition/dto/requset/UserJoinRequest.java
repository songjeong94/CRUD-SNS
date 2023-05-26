package be.project.exhibition.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinRequest {

    private String userId;
    private String password;
    private String userName;
    private String email;

}
