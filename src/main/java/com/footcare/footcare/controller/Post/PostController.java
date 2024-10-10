package com.footcare.footcare.controller.Post;

import com.footcare.footcare.dto.Post.PostDTO;
import com.footcare.footcare.entity.Post.Post;
import com.footcare.footcare.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Optional<PostDTO> postDTO = postService.getPostByIdAndIncreaseView(id);
        return postDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategoryId(@PathVariable Long categoryId) {
        List<PostDTO> posts = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }

    // 특정 게시물의 좋아요 수 증가
    @PutMapping("/{postId}/like")
    public ResponseEntity<PostDTO> increaseLike(@PathVariable Long postId) {
        PostDTO updatedPost = postService.increaseLikeCount(postId);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    // 특정 게시물의 좋아요 수 감소
    @PutMapping("/{postId}/unlike")
    public ResponseEntity<PostDTO> decreaseLike(@PathVariable Long postId) {
        PostDTO updatedPost = postService.decreaseLikeCount(postId);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }
}