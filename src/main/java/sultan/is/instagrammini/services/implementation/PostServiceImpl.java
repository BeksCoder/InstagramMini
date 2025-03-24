package sultan.is.instagrammini.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sultan.is.instagrammini.database.model.Image;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.database.model.User;
import sultan.is.instagrammini.dto.request.PostRequest;
import sultan.is.instagrammini.dto.response.CommentResponseDTO;
import sultan.is.instagrammini.dto.response.ImageResponseDTO;
import sultan.is.instagrammini.dto.response.PostResponse;
import sultan.is.instagrammini.exceptions.PostCreationException;
import sultan.is.instagrammini.exceptions.UserNotFoundException;
import sultan.is.instagrammini.repositories.ImageRepository;
import sultan.is.instagrammini.repositories.PostRepository;
import sultan.is.instagrammini.repositories.UserRepository;
import sultan.is.instagrammini.services.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Override
    public PostResponse createPost(PostRequest postRequestDTO) {
        if (postRequestDTO.getCaption() == null || postRequestDTO.getCaption().isEmpty()) {
            throw new PostCreationException("Caption cannot be empty");
        }
        User user = userRepository.findById(postRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", postRequestDTO.getUserId())));
        Post post = new Post();
        post.setCaption(postRequestDTO.getCaption());
        post.setLocation(postRequestDTO.getLocation());
        post.setPostType(postRequestDTO.getPostType());
        post.setUser(user);
        post.setArchived(false);
        post.setCommentsEnabled(true);
        Post savedPost = postRepository.save(post);
        if (postRequestDTO.getImageUrls() != null && !postRequestDTO.getImageUrls().isEmpty()) {
            for (String imageUrl : postRequestDTO.getImageUrls()) {
                Image image = new Image();
                image.setImageUrl(imageUrl);
                image.setPost(savedPost);

                imageRepository.save(image);


            }
        }

        List<ImageResponseDTO> imageResponseDTOs = savedPost.getImage().stream()
                .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                .toList();

        List<CommentResponseDTO> commentResponseDTOS = savedPost.getComments().stream()
                .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt()))
                .toList();


        return new PostResponse(
                savedPost.getId(),
                savedPost.getCaption(),
                savedPost.getLocation(),
                savedPost.getPostType(),
                savedPost.getCreatedAt(),
                savedPost.isCommentsEnabled(),
                savedPost.isArchived(),
                imageResponseDTOs
                , commentResponseDTOS);

    }

    @Override
    public Optional<PostResponse> getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %d not found", postId)));

        PostResponse responsePost = new PostResponse(
                post.getId(),
                post.getCaption(),
                post.getLocation(),
                post.getPostType(),
                post.getCreatedAt(),
                post.isCommentsEnabled(),
                post.isArchived(),
                post.getImage().stream()
                        .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                        .collect(Collectors.toList()),
                post.getComments().stream()
                        .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt()))
                        .collect(Collectors.toList())
        );
        return Optional.of(responsePost);
    }

    @Override
    public List<PostResponse> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getCaption(),
                        post.getLocation(),
                        post.getPostType(),
                        post.getCreatedAt(),
                        post.isCommentsEnabled(),
                        post.isArchived(),
                        post.getImage().stream()
                                .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                                .toList(),
                        post.getComments().stream()
                                .map(comment -> new CommentResponseDTO(
                                        comment.getId(),
                                        comment.getText(),
                                        comment.getUser().getUsername(),
                                        comment.getCreatedAt()))
                                .toList()
                ))
                .toList();

    }

    @Override
    public List<PostResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getCaption(),
                        post.getLocation(),
                        post.getPostType(),
                        post.getCreatedAt(),
                        post.isCommentsEnabled(),
                        post.isArchived(),
                        post.getImage().stream()
                                .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                                .toList(),
                        post.getComments().stream()
                                .map(comment -> new CommentResponseDTO(
                                        comment.getId(),
                                        comment.getText(),
                                        comment.getUser().getUsername(),
                                        comment.getCreatedAt()))
                                .toList()
                ))
                .toList();
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %d not found", postId)));

        // Обновляем данные поста, если они есть в запросе
        if (request.getCaption() != null && !request.getCaption().isEmpty()) {
            post.setCaption(request.getCaption());
        }
        if (request.getLocation() != null && !request.getLocation().isEmpty()) {
            post.setLocation(request.getLocation());
        }
        if (request.getPostType() != null) {
            post.setPostType(request.getPostType());
        }

        // Обновляем изображения
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            post.getImage().clear(); // Удаляем старые изображения
            List<Image> images = request.getImageUrls().stream()
                    .map(url -> {
                        Image image = new Image();
                        image.setImageUrl(url);
                        image.setPost(post);
                        return image;
                    })
                    .toList();
            post.getImage().addAll(images);
        }

        // Сохраняем обновлённый пост
        Post updatedPost = postRepository.save(post);

        // Формируем ответ
        List<ImageResponseDTO> imageResponseDTOs = updatedPost.getImage().stream()
                .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                .toList();

        List<CommentResponseDTO> commentResponseDTOS = updatedPost.getComments().stream()
                .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt()))
                .toList();

        return new PostResponse(
                updatedPost.getId(),
                updatedPost.getCaption(),
                updatedPost.getLocation(),
                updatedPost.getPostType(),
                updatedPost.getCreatedAt(),
                updatedPost.isCommentsEnabled(),
                updatedPost.isArchived(),
                imageResponseDTOs,
                commentResponseDTOS
        );

}

    @Override
    public void deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new EntityNotFoundException(String.format(
                        "Post with id %d not found",postId
                )));
        postRepository.delete(post);



    }

    @Override
    public PostResponse archivePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %d not found", postId)));

        post.setArchived(true); // Устанавливаем флаг "архивирован"
        postRepository.save(post); // Сохраняем обновленный пост

        return new PostResponse(
                post.getId(),
                post.getCaption(),
                post.getLocation(),
                post.getPostType(),
                post.getCreatedAt(),
                post.isCommentsEnabled(),
                post.isArchived(), // Будет true
                post.getImage().stream()
                        .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                        .toList(),
                post.getComments().stream()
                        .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt()))
                        .toList());
    }

    @Override
    public PostResponse toggleComments(Long postId, boolean enableComments) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new EntityNotFoundException("Post with id "+postId+ " not found"));

        post.setCommentsEnabled(enableComments);
        postRepository.save(post);
        return new PostResponse(
                post.getId(),
                post.getCaption(),
                post.getLocation(),
                post.getPostType(),
                post.getCreatedAt(),
                post.isCommentsEnabled(),
                post.isArchived(),
                post.getImage().stream()
                        .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                        .toList(),
                post.getComments().stream()
                        .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt()))
                        .toList()
        );
    }

    @Override
    public List<PostResponse> getPostsByType(Long userId, Post.PostType postType) {
        List<Post> posts  = postRepository.findAllByUserId(userId);
        List<Post> filteredPosts = posts.stream()
                .filter(post -> post.getPostType().equals(postType))
                .toList();

        return filteredPosts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getCaption(),
                        post.getLocation(),
                        post.getPostType(),
                        post.getCreatedAt(),
                        post.isCommentsEnabled(),
                        post.isArchived(),
                        post.getImage().stream()
                                .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getDescription()))
                                .collect(Collectors.toList()),
                        post.getComments().stream()
                                .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(), comment.getUser().getUsername(), comment.getCreatedAt()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }


}
