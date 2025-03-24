package sultan.is.instagrammini.services.implementation;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.is.instagrammini.database.model.Comment;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.database.model.User;
import sultan.is.instagrammini.dto.request.CommentRequestDTO;
import sultan.is.instagrammini.dto.response.CommentResponseDTO;
import sultan.is.instagrammini.exceptions.InvalidCommentException;
import sultan.is.instagrammini.exceptions.UserNotFoundException;
import sultan.is.instagrammini.repositories.CommentRepository;
import sultan.is.instagrammini.repositories.PostRepository;
import sultan.is.instagrammini.repositories.UserRepository;
import sultan.is.instagrammini.services.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDTO addComment(CommentRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", requestDTO.getUserId())));
        Post post = postRepository.findById(requestDTO.getPostId()).
                orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %d not found", requestDTO.getPostId())));
        String commentText = requestDTO.getText();
        if (commentText == null || commentText.trim().isEmpty()) {
            throw new InvalidCommentException("Comment text cannot be empty or blank.");
        }
        Comment comment = new Comment();
        if (commentText.length() >500) {
            throw new InvalidCommentException("Comment text cannot exceed 500 characters.");
        }
        if(!post.isCommentsEnabled()){
            throw new InvalidCommentException("Comments are disabled for this post.");
        }
        Comment newComment = new Comment();
        comment.setText(commentText);
        comment.setUser(user);
        comment.setPost(post);
        post.getComments().add(comment);
        commentRepository.save(comment);
        return new CommentResponseDTO(newComment.getId(), newComment.getText(), newComment.getUser().getUsername(), newComment.getCreatedAt());
    }

    @Override
    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        return List.of();
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {

    }

    @Override
    public CommentResponseDTO updateComment(CommentRequestDTO requestDTO) {
        return null;
    }

    @Override
    public CommentResponseDTO getCommentById(Long commentId) {
        Optional<Comment> opt = commentRepository.findById(commentId);
        if (opt.isPresent()) {
            Comment comment = opt.get();
            return new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt());

        } else {
            throw new EntityNotFoundException("Comment with id " + commentId + " not found");
        }

    }

    @Override
    public long getCommentCount(Long postId) {
        List<Comment> comments = commentRepository.findCommentByPostId(postId);
        return comments != null ? comments.size() : 0;
    }

    @Override
    public String translateComment(String text, String targetLanguage) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(text,
                Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }
}
