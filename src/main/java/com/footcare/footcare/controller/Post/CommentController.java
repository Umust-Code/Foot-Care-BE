package com.footcare.footcare.controller.Post;

import com.footcare.footcare.dto.Post.CommentDTO;
import com.footcare.footcare.service.Post.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 특정 게시물에 대한 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 특정 게시물에 댓글 생성
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(postId, commentDTO);
        return createdComment != null ? ResponseEntity.ok(createdComment) : ResponseEntity.notFound().build();
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO);
        return updatedComment != null ? ResponseEntity.ok(updatedComment) : ResponseEntity.notFound().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

