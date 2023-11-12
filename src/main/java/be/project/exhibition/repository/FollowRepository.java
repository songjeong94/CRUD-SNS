package be.project.exhibition.repository;

import be.project.exhibition.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    @Modifying
    @Query(value = "insert into FollowEntity(toUserId, fromUserId, createDate)" +
            "VALUES(:toUserId, :fromUserId, now())", nativeQuery = true)
    void save(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId);

    @Modifying
    @Query(value = "delete from FollowEntity where toUserId = :toUserId and fromUserId = :fromUserId")
    void deleteByToUserIdAndFromUserId(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId);

    int countByToUserId(String toUserId);

    boolean existsByToUserIdAndFromUserId(String toUserId, String fromUserId);

}
