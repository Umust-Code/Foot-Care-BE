package com.footcare.footcare.Repository.Post;

import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 카테고리 ID로 게시물 조회
    List<Post> findByCategoryCategoryId(Long categoryId);

    @Query(value = "SELECT * FROM post ORDER BY like_count DESC LIMIT 5", nativeQuery = true)
    List<Post> findTop5ByOrderByLikeCountDesc();



}