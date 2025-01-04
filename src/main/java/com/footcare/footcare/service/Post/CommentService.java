package com.footcare.footcare.service.Post;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.Repository.Post.CommentRepository;
import com.footcare.footcare.Repository.Post.PostRepository;
import com.footcare.footcare.dto.Member.MemberDTO;
import com.footcare.footcare.dto.Post.CommentDTO;
import com.footcare.footcare.entity.Member.Member;
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

    @Autowired
    private MemberRepository memberRepository;

    // Entity -> DTO 변환 메서드
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setPostId(comment.getPost().getPostId());
        dto.setCommentContent(comment.getCommentContent());
        dto.setCommentDate(comment.getCommentDate());
        dto.setMemberId(comment.getMember().getMemberId());  // MemberId 추가
        return dto;
    }

    // DTO -> Entity 변환 메서드
    private Comment convertToEntity(CommentDTO dto, Post post, Member member) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(member);  // Member 설정
        comment.setCommentContent(dto.getCommentContent());
        comment.setCommentDate(new Date());
        return comment;
    }

    // Member -> MemberDTO 변환 메서드
    private MemberDTO convertToMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(member.getMemberId());
        memberDTO.setId(member.getId());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setName(member.getName());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setAddress(member.getAddress());
        return memberDTO;
    }

    // 특정 게시물에 대한 모든 댓글 조회
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            List<Comment> comments = commentRepository.findByPost(postOptional.get());
            return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        return List.of();
    }

    // 댓글 생성
    public CommentDTO createComment(Long postId, CommentDTO commentDTO, Long memberId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Optional<Member> memberOptional = memberRepository.findById(memberId);  // memberId로 Member 조회

        if (postOptional.isPresent() && memberOptional.isPresent()) {
            Comment comment = convertToEntity(commentDTO, postOptional.get(), memberOptional.get());
            Comment savedComment = commentRepository.save(comment);
            return convertToDTO(savedComment);
        }
        return null;
    }

    // 댓글 수정
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

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // 댓글에 해당하는 Member 조회
    public MemberDTO getMemberByCommentId(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            return convertToMemberDTO(comment.getMember());
        }
        return null;
    }
}
