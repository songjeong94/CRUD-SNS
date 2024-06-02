package be.project.exhibition.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
    private String checkPassword;

}
