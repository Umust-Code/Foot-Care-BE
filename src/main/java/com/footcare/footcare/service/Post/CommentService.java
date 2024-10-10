package com.footcare.footcare.service.Post;

import com.footcare.footcare.Repository.Post.CommentRepository;
import com.footcare.footcare.Repository.Post.PostRepository;
import com.footcare.footcare.dto.Post.CommentDTO;
import com.footcare.footcare.entity.Post.Comment;
import com.footcare.footcare.entity.Post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // Entity -> DTO 변환 메서드
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setPostId(comment.getPost().getPostId());
        dto.setCommentContent(comment.getCommentContent());
        dto.setCommentDate(comment.getCommentDate());
        return dto;
    }

    // DTO -> Entity 변환 메서드
    private Comment convertToEntity(CommentDTO dto, Post post) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setCommentContent(dto.getCommentContent());
        comment.setCommentDate(new Date());
        return comment;
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            List<Comment> comments = commentRepository.findByPost(postOptional.get());
            return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        return List.of();
    }

    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Comment comment = convertToEntity(commentDTO, postOptional.get());
            Comment savedComment = commentRepository.save(comment);
            return convertToDTO(savedComment);
        }
        return null;
    }

    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setCommentContent(commentDTO.getCommentContent());
            comment.setCommentDate(new Date());  // 수정된 날짜로 설정
            Comment updatedComment = commentRepository.save(comment);
            return convertToDTO(updatedComment);
        }
        return null;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
