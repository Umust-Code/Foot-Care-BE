package com.footcare.footcare.dto.Shopping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductReviewDTO {

    private String productReviewId;
    private int productReviewRate;
    private String productReviewContent;
    private Date productReviewDate;

}

