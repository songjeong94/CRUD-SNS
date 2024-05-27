package be.project.exhibition.entity;

import be.project.exhibition.constant.UserRole;
import be.project.exhibition.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Id
    @Column(length = 50)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String name;

    @Column
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private UserImage profileImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostEntity> postList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<FollowEntity> followerList;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<FollowEntity> followingList;


    public static UserEntity of(String userId, String password, String userName, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setPassword(password);
        userEntity.setName(userName);
        userEntity.setEmail(email);
        return userEntity;
    }

    public static UserEntity fromDto(UserDto userDto) {
        return new UserEntity().of(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
    }

}
