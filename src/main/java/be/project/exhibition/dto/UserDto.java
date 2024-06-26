package be.project.exhibition.dto;

import be.project.exhibition.constant.UserRole;
import be.project.exhibition.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements UserDetails {

    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private UserRole userRole;

    public static UserDto fromEntity(UserEntity userEntity) {
        return new UserDto(
                userEntity.getUserId(),
                userEntity.getPassword(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getCreatedAt(),
                userEntity.getRole());
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getUserRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.getUserId();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
