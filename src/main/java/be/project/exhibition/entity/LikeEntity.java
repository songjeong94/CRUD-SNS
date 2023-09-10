package be.project.exhibition.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "heart")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    public static LikeEntity of(UserEntity user, PostEntity post) {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.userEntity = user;
        likeEntity.postEntity = post;
        return likeEntity;
    }

}
