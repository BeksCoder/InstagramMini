package sultan.is.instagrammini.conntrollers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.exceptions.UnauthorizedActionException;
import sultan.is.instagrammini.services.LikeService;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<Void> likeAdd(@RequestParam Long userId, @RequestParam Long postId) {
        likeService.addLike(userId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeLike(@RequestParam Long userId, @RequestParam Long postId) {
        likeService.removeLike(userId, postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countLike(@RequestParam Long postId){
       long likesCount =  likeService.getLikesCount(postId);
        return ResponseEntity.ok(likesCount);
    }
    @GetMapping("/isLiked")
    public ResponseEntity<Boolean> isLiked(@RequestParam Long userId,@RequestParam Long postId){
        boolean isLiked = likeService.isLikedByUser(userId,postId);
        return ResponseEntity.ok(isLiked);
    }

}
