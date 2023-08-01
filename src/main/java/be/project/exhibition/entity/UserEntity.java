package be.project.exhibition.entity;

import be.project.exhibition.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity {

    @Id
    @Column
    private String userId;

    @Column
    private String password;

    @Column
    private String userName;

    @Column
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    public static UserEntity of(String userId, String password, String userName, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setPassword(password);
        userEntity.setUserName(userName);
        userEntity.setEmail(email);
        return userEntity;
    }

    public static UserEntity fromDto(UserDto userDto) {
        return new UserEntity().of(userDto.getUserId(), userDto.getPassword(), userDto.getUserName(), userDto.getEmail());
    }

}
