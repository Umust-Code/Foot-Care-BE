package com.footcare.footcare.entity.Shopping;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "shoppingCategory")
@Getter
@Setter
public class ShoppingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shcategoryId;

    private String shcategoryName;

    @OneToMany(mappedBy = "shoppingCategory", cascade = CascadeType.ALL)
    private List<Product> products;


}
