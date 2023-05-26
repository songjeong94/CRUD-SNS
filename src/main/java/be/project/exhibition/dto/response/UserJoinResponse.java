package be.project.exhibition.dto.response;

import be.project.exhibition.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {

    private String userId;
    private String password;
    private String userName;
    private String email;

    public static UserJoinResponse fromUserDto(UserDto userDto) {
        return new UserJoinResponse(
                userDto.getUserId(),
                userDto.getPassword(),
                userDto.getUserName(),
                userDto.getEmail());
    }

}
