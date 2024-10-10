package com.footcare.footcare.Repository.Post;

import com.footcare.footcare.entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}