package sultan.is.instagrammini.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sultan.is.instagrammini.database.model.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String caption;
    private String location;
    private Post.PostType postType;
    private LocalDateTime createdAt;
    private boolean commentsEnabled;
    private boolean archived;
    private List<ImageResponseDTO> images;
    private List<CommentResponseDTO>commentResponseDTOS;
}
