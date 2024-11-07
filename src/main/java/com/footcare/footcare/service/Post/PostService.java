package com.footcare.footcare.service.Post;

import com.footcare.footcare.Repository.Post.PostRepository;

import com.footcare.footcare.dto.Post.PostDTO;
import com.footcare.footcare.entity.Post.Post;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // Entity -> DTO 변환
    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setCategoryId(post.getCategory_id());
        dto.setPostName(post.getPostName());
        dto.setPostContentName(post.getPostContentName());
        dto.setPostDate(post.getPostDate());
        dto.setPostView(post.getPostView());
        dto.setLikeCount(post.getLikeCount());
        return dto;
    }

    // DTO -> Entity 변환
    private Post convertToEntity(PostDTO dto) {
        Post post = new Post();
        post.setPostId(dto.getPostId());
        post.setCategory_id(dto.getCategoryId());
        post.setPostName(dto.getPostName());
        post.setPostContentName(dto.getPostContentName());
        post.setPostDate(dto.getPostDate());
        post.setPostView(dto.getPostView());
        return post;
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setPostName(postDTO.getPostName());
            post.setPostContentName(postDTO.getPostContentName());
            post.setPostDate(postDTO.getPostDate());
            post.setLikeCount(postDTO.getLikeCount());
            Post updatedPost = postRepository.save(post);
            return convertToDTO(updatedPost);
        } else {
            return null;
        }
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 게시물 조회 시 조회수 증가
    @Transactional
    public Optional<PostDTO> getPostByIdAndIncreaseView(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setPostView(post.getPostView() + 1);
            postRepository.save(post);
            return Optional.of(convertToDTO(post));
        }
        return Optional.empty();
    }

    // 카테고리 ID로 게시물 조회
    public List<PostDTO> getPostsByCategoryId(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryCategoryId(categoryId);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 좋아요 수 증가 (DTO 반환)
    public PostDTO increaseLikeCount(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setLikeCount((post.getLikeCount() != null ? post.getLikeCount() : 0L) + 1);  // 기존 값에 1 증가
            Post updatedPost = postRepository.save(post);
            return convertToDTO(updatedPost);
        }
        return null;
    }

    // 좋아요 수 감소 (DTO 반환)
    public PostDTO decreaseLikeCount(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            // likeCount가 null일 경우 기본값 0L로 간주하고, 0보다 큰 경우에만 감소
            Long currentLikeCount = (post.getLikeCount() != null) ? post.getLikeCount() : 0L;
            if (currentLikeCount > 0) {
                post.setLikeCount(currentLikeCount - 1);
            }
            Post updatedPost = postRepository.save(post);
            return convertToDTO(updatedPost);
        }
        return null;
    }

}
