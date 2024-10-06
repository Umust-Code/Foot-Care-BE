package com.footcare.footcare.entity.Shopping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shoppingProductReview")
@Getter
@Setter
public class ShoppingProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingProductReviewId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private String productReviewId;

    @OneToOne(mappedBy = "shoppingProductReview", cascade = CascadeType.ALL)
    private ProductReview productReview;
}
