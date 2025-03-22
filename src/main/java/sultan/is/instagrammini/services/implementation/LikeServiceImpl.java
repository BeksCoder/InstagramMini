package sultan.is.instagrammini.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sultan.is.instagrammini.database.model.Like;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.database.model.User;
import sultan.is.instagrammini.exceptions.AlreadyLikedException;
import sultan.is.instagrammini.exceptions.UserNotFoundException;
import sultan.is.instagrammini.repositories.LikeRepository;
import sultan.is.instagrammini.repositories.PostRepository;
import sultan.is.instagrammini.repositories.UserRepository;
import sultan.is.instagrammini.services.LikeService;

import java.util.List;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void addLike(Long userId, Long postId) {
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId,postId);
        if (alreadyLiked){
            log.warn("User {} has already liked post {}", userId, postId);
            throw new AlreadyLikedException(String.format("User has already this post id %d,",postId));
        }
        User user  = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(String.format("User with id %d not found",userId)));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + postId));

   Like like =   Like.builder()
             .user(user)
             .post(post)
             .build();
   likeRepository.save(like);
        log.info("Like added by user {} on post {}", userId, postId);



    }

    @Override
    public void removeLike(Long userId, Long postId) {

    }

    @Override
    public long getLikesCount(Long postId) {
        return 0;
    }

    @Override
    public boolean isLikedByUser(Long userId, Long postId) {
        return false;
    }

    @Override
    public List<Long> getUserLikes(Long userId) {
        return List.of();
    }

    @Override
    public List<Long> getPostLikes(Long postId) {
        return List.of();
    }
}
