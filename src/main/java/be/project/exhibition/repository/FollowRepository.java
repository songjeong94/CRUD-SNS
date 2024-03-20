package be.project.exhibition.repository;

import be.project.exhibition.entity.FollowEntity;
import be.project.exhibition.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    List<FollowEntity> findAllByFromUser(UserEntity user);

    List<FollowEntity> findAllByToUser(UserEntity user);

    void deleteFollowEntityByFromUser(UserEntity user);
    @Query("select f from FollowEntity f where f.fromUser = :from and f.toUser = :to")
    Optional<FollowEntity> findFollow(@Param("from") UserEntity fromUser, @Param("to") UserEntity toUser);

}
