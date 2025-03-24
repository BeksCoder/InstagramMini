package sultan.is.instagrammini.services;

import sultan.is.instagrammini.dto.request.CommentRequestDTO;
import sultan.is.instagrammini.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {

        // Создание нового комментария
        CommentResponseDTO addComment(CommentRequestDTO requestDTO);

        // Получить все комментарии для поста
        List<CommentResponseDTO> getCommentsByPostId(Long postId);

        // Удалить комментарий
        void deleteComment(Long commentId, Long userId);  // Обычно пользователи могут удалять только свои комментарии

        // Обновить текст комментария
        CommentResponseDTO updateComment(CommentRequestDTO requestDTO);

        // Получить комментарий по ID
        CommentResponseDTO getCommentById(Long commentId);

        // Получить количество комментариев для поста
        long getCommentCount(Long postId);

        // Перевести комментарий с русского на английский и наоборот
        String translateComment(String text, String targetLanguage);
    }


