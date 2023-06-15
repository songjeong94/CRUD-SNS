package be.project.exhibition.repository;

import be.project.exhibition.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserId(String userId);

}
