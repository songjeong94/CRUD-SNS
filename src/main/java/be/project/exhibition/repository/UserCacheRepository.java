package be.project.exhibition.repository;

import be.project.exhibition.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, UserDto> userDtoRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(UserDto user) {
        String key= getKey(user.getName());
        log.info("Set User to Redis {}, {}", key, user);
        userDtoRedisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    public Optional<UserDto> getUser(String userName) {
        String key = getKey(userName);
        UserDto user = userDtoRedisTemplate.opsForValue().get(key);
        log.info("Get User to Redis {}, {}", key ,user );
        return Optional.ofNullable(user);
    }

    private String getKey(String userName) {
        return "USER:" + userName;
    }

}
