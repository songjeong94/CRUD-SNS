package be.project.exhibition.entity;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@Table(name = "post", indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt")
})
public class PostEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "postEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<PostImage> postImages;


    public PostEntity() { }

    public static PostEntity of(String title, String body, UserDto user) {
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
