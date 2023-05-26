package be.project.exhibition.dto;

import be.project.exhibition.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String userId;
    private String password;
    private String userName;
    private String email;

    public static UserDto fromEntity(UserEntity userEntity) {
        return new UserDto(
                userEntity.getUserId(),
                userEntity.getPassword(),
                userEntity.getUserName(),
                userEntity.getEmail());
    }
}
