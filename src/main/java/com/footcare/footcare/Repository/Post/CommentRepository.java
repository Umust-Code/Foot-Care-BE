package com.footcare.footcare.Repository.Post;

import com.footcare.footcare.entity.Post.Comment;
import com.footcare.footcare.entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByMemberMemberId(Long memberId);
    List<Comment> findByCommentContentContaining(String content);
    List<Comment> findByMemberMemberIdAndCommentContentContaining(Long memberId, String content);
}
