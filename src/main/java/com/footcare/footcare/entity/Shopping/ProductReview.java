package com.footcare.footcare.entity.Shopping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "productReview")
@Getter
@Setter
public class ProductReview {

    @Id
    private String productReviewId;

    @OneToOne
    @JoinColumn(name = "shoppingProductReviewId")
    private ShoppingProductReview shoppingProductReview;

    private int productReviewRate;

    private String productReviewContent;

    @Temporal(TemporalType.DATE)
    private Date productReviewDate;
}
