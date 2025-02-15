package com.footcare.footcare.controller.Post;

import com.footcare.footcare.dto.Post.PostDTO;
import com.footcare.footcare.entity.Post.Post;
import com.footcare.footcare.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        Map<String, Object> result = postService.createPost(postDTO);

        if ("EXCEEDED_LIMIT".equals(result.get("status"))) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/0")
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

    // 게시물에 좋아요 추가
    @PostMapping("/{postId}/like/{memberId}")
    public ResponseEntity<PostDTO> likePost(@PathVariable Long memberId, @PathVariable Long postId) {
        PostDTO updatedPost = postService.likePost(memberId, postId);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.badRequest().build();
    }

    // 게시물에 좋아요 취소
    @DeleteMapping("/{postId}/unlike/{memberId}")
    public ResponseEntity<PostDTO> unlikePost(@PathVariable Long memberId, @PathVariable Long postId) {
        PostDTO updatedPost = postService.unlikePost(memberId, postId);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.badRequest().build();
    }

    // 사용자 별 어느 게시물에 좋아요 눌렀는지
    @GetMapping("/liked-by-user/{memberId}")
    public ResponseEntity<List<PostDTO>> getPostsLikedByMember(@PathVariable Long memberId) {
        List<PostDTO> likedPosts = postService.getPostsLikedByMember(memberId);
        return ResponseEntity.ok(likedPosts);
    }

    // order by 좋아요 desc limit 5
    @GetMapping("/top-liked")
    public ResponseEntity<List<PostDTO>> getTop5LikedPosts() {
        List<PostDTO> topPosts = postService.getTop5PostsByLikeCount();
        return ResponseEntity.ok(topPosts);
    }

    @GetMapping("/{postId}/is-liked/{memberId}")
    public ResponseEntity<String> isPostLiked(@PathVariable Long memberId, @PathVariable Long postId) {
        String likeStatus = postService.isPostLikedByUser(memberId, postId);
        return ResponseEntity.ok(likeStatus);
    }
}