package be.project.exhibition.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table (
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "follow_entity_uk",
                        columnNames = {"toUserId", "fromUserId"}
                )
        }
)
public class FollowEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "toUserId")
    private String toUserId;

    @JoinColumn(name = "fromUserId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity fromUserId;

    @CreationTimestamp
    private Timestamp createDate;

    public static FollowEntity of(String toUserId, UserEntity fromUser) {
        FollowEntity follow = new FollowEntity();
        follow.setToUserId(toUserId);
        follow.setFromUserId(fromUser);
        return follow;
    }
}
