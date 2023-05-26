package be.project.exhibition.fixture;

import be.project.exhibition.entity.UserEntity;

public class UserFixture {

    public static UserEntity get(String userId, String password, String userName, String email) {
        UserEntity result = new UserEntity();
        result.setUserId(userId);
        result.setPassword(password);
        result.setUserName(userName);
        result.setEmail(email);

        return result;
    }
}
