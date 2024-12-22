package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;

import java.util.List;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor@AllArgsConstructor
@Builder
public class Follower extends BaseEntity{
    private List<Long> subscribes;
    private List<Long> subscriptions;




}
