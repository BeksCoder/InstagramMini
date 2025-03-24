package sultan.is.instagrammini.services;

import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.dto.request.CommentRequestDTO;
import sultan.is.instagrammini.dto.request.PostRequest;
import sultan.is.instagrammini.dto.response.CommentResponseDTO;
import sultan.is.instagrammini.dto.response.ImageResponseDTO;
import sultan.is.instagrammini.dto.response.PostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostResponse createPost(PostRequest postRequestDTO);

    Optional<PostResponse> getPostById(Long postId);

    List<PostResponse> getPostsByUserId(Long userId);

    List<PostResponse> getAllPosts(int page, int size);

    PostResponse updatePost(Long postId, PostRequest request);

    void deletePost(Long postId);

    PostResponse archivePost(Long postId);

    PostResponse toggleComments(Long postId, boolean enableComments);

    List<PostResponse> getPostsByType(Long userId, Post.PostType postType);
    List<PostResponse> getPostsByLocation(String location);
    List<PostResponse> getPostsByHashtag(String hashtag);





}
