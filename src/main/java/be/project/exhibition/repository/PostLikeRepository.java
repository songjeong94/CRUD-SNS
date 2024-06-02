package be.project.exhibition.repository;

import be.project.exhibition.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {

    Optional<PostLikeEntity> findByPostIdAndAndUserUserId(Long postId, String userId);
    long countByPostId(Long post);
}
