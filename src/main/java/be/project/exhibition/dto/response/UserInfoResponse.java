package be.project.exhibition.dto.response;

import be.project.exhibition.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserInfoResponse {

    private String userId;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private String image;

    public static UserInfoResponse fromEntity(UserEntity user) {
        return new UserInfoResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getProfileImage().getStoredImage()
        );
    }
}
