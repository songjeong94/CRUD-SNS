package be.project.exhibition.entity;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@DynamicInsert
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ColumnDefault("0")
    @Column(name = "likes", nullable = false)
    private Integer likes;

    @ColumnDefault("0")
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    public PostEntity() {

    }

    public PostEntity of(String title, String body, UserDto user) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setBody(body);
        postEntity.setUser(UserEntity.of(user.getUserId(), user.getPassword(), user.getName(), user.getEmail()));
        return postEntity;
    }


    public static PostEntity of(String title, String body, UserEntity user) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setBody(body);
        postEntity.setUser(user);
        return postEntity;
    }

    public static PostEntity fromDto(PostDto postDto) {
        return new PostEntity().of(postDto.getTitle(), postDto.getBody(), postDto.getUser());
    }
}
