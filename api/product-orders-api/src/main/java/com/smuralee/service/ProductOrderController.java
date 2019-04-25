package com.smuralee.service;

import com.smuralee.entity.ProductOrder;
import com.smuralee.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-orders")
public class ProductOrderController {

    @Autowired
    private ProductOrderRepository repository;

    @GetMapping
    public List<ProductOrder> list() {
        return this.repository.findAll();
    }
}
