package be.project.exhibition.entity;

import be.project.exhibition.dto.PostDto;
import be.project.exhibition.dto.UserDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<LikeEntity> likesList = new ArrayList<>();

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
