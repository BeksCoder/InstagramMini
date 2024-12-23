package sultan.is.instagrammini.database.model;

import jakarta.persistence.*;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@SequenceGenerator(name = "base_id_gen", sequenceName = "like_seq", allocationSize = 1)

public class Like extends BaseEntity {
    private boolean IsLike;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
