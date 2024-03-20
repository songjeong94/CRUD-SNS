package be.project.exhibition.repository;

import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findById(Long postId);
    Page<PostEntity> findAllByUser(UserEntity user, Pageable pageable);

}
