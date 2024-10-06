package com.footcare.footcare.entity.Shopping;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "shcategoryId")
    private ShoppingCategory shoppingCategory;

    private String productName;

    private String productContent;

    private String productImage;

    @Temporal(TemporalType.DATE)
    private Date productDate;

    private Long productCost;

    private Long productCount;

    private Long reviewCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ShoppingProductReview> shoppingProductReviews;

}

