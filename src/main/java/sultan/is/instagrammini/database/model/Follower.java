package sultan.is.instagrammini.database.model;

import jakarta.persistence.*;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "base_id_gen", sequenceName = "follower_seq", allocationSize = 1)

public class Follower extends BaseEntity {
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> subscribes;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> subscriptions;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
