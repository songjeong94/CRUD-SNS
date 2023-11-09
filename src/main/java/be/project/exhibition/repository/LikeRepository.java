package be.project.exhibition.repository;

import be.project.exhibition.entity.LikeEntity;
import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Optional<LikeEntity> existsByUserEntityAndPostEntity(UserEntity user, PostEntity post);

    void delete(LikeEntity likeEntity);
}
