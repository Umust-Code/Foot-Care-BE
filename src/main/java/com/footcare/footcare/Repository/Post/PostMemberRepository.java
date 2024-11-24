package com.footcare.footcare.Repository.Post;

import com.footcare.footcare.entity.Post.Post;
import com.footcare.footcare.entity.Post.PostMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostMemberRepository extends JpaRepository<PostMember, Long> {

    @Query("SELECT pm FROM PostMember pm WHERE pm.member.memberId = :memberId AND pm.post.postId = :postId")
    Optional<PostMember> findByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);

    @Query("SELECT pm.post FROM PostMember pm WHERE pm.member.memberId = :memberId AND pm.likefg = 'Y'")
    List<Post> findPostsLikedByMember(@Param("memberId") Long memberId);

    @Query("SELECT pm.likefg FROM PostMember pm WHERE pm.member.memberId = :memberId AND pm.post.postId = :postId")
    Optional<String> findLikeStatusByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);

}

