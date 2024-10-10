package com.footcare.footcare.Repository.Post;

import com.footcare.footcare.entity.Post.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

