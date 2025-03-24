package sultan.is.instagrammini.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sultan.is.instagrammini.database.model.Post;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String caption;
    private String location;
    private Post.PostType postType;
    private Long userId;
}
