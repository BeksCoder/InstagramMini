package sultan.is.instagrammini.conntrollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.dto.request.PostRequest;
import sultan.is.instagrammini.dto.response.PostResponse;
import sultan.is.instagrammini.services.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {

        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);


    }

    @GetMapping("/{id}/get-by-id")
    public ResponseEntity<Optional<PostResponse>> getPostById(@PathVariable Long id) {
        Optional<PostResponse> response = postService.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pag")
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PostResponse> posts = postService.getAllPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable Long userId) {
        List<PostResponse> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
    @PutMapping("/{postId}/toggle-comments")
    public ResponseEntity<PostResponse> toggleComments(@PathVariable Long postId, @RequestParam boolean enableComments) {
        PostResponse updatedPost = postService.toggleComments(postId, enableComments);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/user/{userId}/type/{postType}")
    public ResponseEntity<List<PostResponse>> getPostsByType(
            @PathVariable Long userId,
            @PathVariable Post.PostType postType
    ) {
        List<PostResponse> posts = postService.getPostsByType(userId, postType);
        return ResponseEntity.ok(posts);
    }
}
