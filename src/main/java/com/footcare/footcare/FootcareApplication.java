package com.footcare.footcare;

import com.footcare.footcare.Repository.Post.CategoryRepository;
import com.footcare.footcare.entity.Post.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FootcareApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(FootcareApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Category> categories = Arrays.asList(
				new Category(1L, "족저근막염"),
				new Category(2L, "발부종"),
				new Category(3L, "당뇨병성 발"),
				new Category(4L, "발 뒤꿈치 통증"),
				new Category(5L, "무좀(발냄새)"),
				new Category(6L, "발목 염좌"),
				new Category(7L, "생활 습관"),
				new Category(8L, "건강 정보"),
				new Category(9L, "제품 추천")
		);

		categoryRepository.saveAll(categories);
		System.out.println("초기 카테고리 데이터가 삽입되었습니다.");
	}
}