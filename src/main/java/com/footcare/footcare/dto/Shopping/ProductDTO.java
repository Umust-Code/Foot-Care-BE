package com.footcare.footcare.dto.Shopping;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private Long shcategoryId;
    private String productName;
    private String productContent;
    private String productImage;
    private Date productDate;
    private Long productCost;
    private Long productCount;
    private Long reviewCount;
}

