package sultan.is.instagrammini.database.model;

import jakarta.persistence.*;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;

import java.util.List;

@Entity
@Table(name = "followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "base_id_gen", sequenceName = "follower_seq", allocationSize = 1)
public class Follower extends BaseEntity {

                            @ManyToOne(fetch = FetchType.LAZY)
                            @JoinColumn(name = "user_id")  // Maps to the user being followed
                            private User user;

    @ManyToMany
    @JoinTable(
            name = "followers_subscribes",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_user_id"))
    private List<User> subscribes;  // Users whom the user follows

    @ManyToMany
    @JoinTable(
            name = "followers_subscriptions",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> subscriptions;  // Users who follow the user
}