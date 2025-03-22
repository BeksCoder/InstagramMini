package sultan.is.instagrammini.conntrollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sultan.is.instagrammini.database.model.Like;
import sultan.is.instagrammini.services.LikeService;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<Void> likeAdd(@RequestParam Long userId, @RequestParam Long postId) {
        likeService.addLike(userId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }


}
