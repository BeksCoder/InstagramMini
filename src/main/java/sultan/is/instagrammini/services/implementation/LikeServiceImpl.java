package sultan.is.instagrammini.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sultan.is.instagrammini.database.model.Like;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.database.model.User;
import sultan.is.instagrammini.exceptions.AlreadyLikedException;
import sultan.is.instagrammini.exceptions.UnauthorizedActionException;
import sultan.is.instagrammini.exceptions.UserNotFoundException;
import sultan.is.instagrammini.repositories.LikeRepository;
import sultan.is.instagrammini.repositories.PostRepository;
import sultan.is.instagrammini.repositories.UserRepository;
import sultan.is.instagrammini.services.LikeService;

import java.util.List;
import java.util.stream.Collectors;

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
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);
        if (alreadyLiked) {
            log.warn("User {} has already liked post {}", userId, postId);
            throw new AlreadyLikedException(String.format("User has already this post id %d,", postId));
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", userId)));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + postId));

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        likeRepository.save(like);
        log.info("Like added by user {} on post {}", userId, postId);


    }

    @Override
    public void removeLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", userId)));
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %d not found", postId)));
        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new EntityNotFoundException("Like not found for the given user and post"));
        if (!like.getUser().equals(user)) {
            throw new UnauthorizedActionException("User is not authorized to remove this like");
        }
        likeRepository.delete(like);
        log.info("Like removed by user {} from post {}. Like ID: {}", userId, postId, like.getId());
    }

    @Override
    public long getLikesCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public boolean isLikedByUser(Long userId, Long postId) {
        boolean liked = likeRepository.existsByUserIdAndPostId(userId, postId);
        if (liked) {
            log.info("User {} has liked post {}", userId, postId);
        } else {
            log.info("User {} has not liked post {}", userId, postId);
        }
        return liked;
    }

    @Override
    public List<Long> getUserLikes(Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);

        return likes.stream()
                .map(like -> like.getPost().getId())
                .toList();
    }

    @Override
    public List<Long> getPostLikes(Long postId) {
      List<Like> likes =   likeRepository.findByPostId(postId);
      if(likes.isEmpty()){
          log.info("No likes found for post {}", postId);
          return List.of();
      }
       return likes.stream().map(like -> like.getUser().getId()).toList();
    }
}
