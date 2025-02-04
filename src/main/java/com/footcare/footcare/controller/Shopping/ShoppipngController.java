package com.footcare.footcare.controller.Shopping;

import com.footcare.footcare.service.Shopping.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api/shopping")
public class ShoppipngController {

    @Autowired
    private ShoppingService shoppingService;

    @GetMapping("/products")
    public ResponseEntity<String> getProductInfo() {
        String response = shoppingService.getProductInfo();
        return ResponseEntity.ok(response);
    }
}
