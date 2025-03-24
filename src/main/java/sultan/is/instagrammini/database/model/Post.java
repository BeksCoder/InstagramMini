package sultan.is.instagrammini.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import sultan.is.instagrammini.database.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "base_id_gen", sequenceName = "post_seq", allocationSize = 1)

public class Post extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String caption;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    private String location;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private boolean commentsEnabled = true;

    private boolean archived = false;

    public enum PostType {
        IMAGE, VIDEO, CAROUSEL
    }
}

