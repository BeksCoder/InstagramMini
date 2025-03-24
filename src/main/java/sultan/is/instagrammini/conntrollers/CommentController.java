package sultan.is.instagrammini.conntrollers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sultan.is.instagrammini.services.CommentService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/translate")
    public ResponseEntity<String> translateComment(@RequestParam String text,
                                                   @RequestParam String targetLanguage) {
        try {
            String translatedText = commentService.translateComment(text, targetLanguage);
            return ResponseEntity.ok(translatedText);
        } catch (Exception e) {
            log.error("Error translating comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error translating comment");
        }
    }
}