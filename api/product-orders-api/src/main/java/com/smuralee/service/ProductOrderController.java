package com.smuralee.service;

import com.smuralee.entity.ProductOrder;
import com.smuralee.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-orders")
public class ProductOrderController {

    @Autowired
    private ProductOrderRepository repository;

    @GetMapping
    public List<ProductOrder> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ProductOrder getById(final @PathVariable Long id) {
        Optional<ProductOrder> productOrder = this.repository.findById(id);
        return productOrder.get();
    }

    @PostMapping
    public ProductOrder addProductOrder(final @RequestBody ProductOrder productOrder) {
        return this.repository.save(productOrder);
    }

    @PutMapping("/{id}")
    public ProductOrder updateProductOrder(final @RequestBody ProductOrder productOrder, final @PathVariable Long id) {
        Optional<ProductOrder> fetchedProductOrder = this.repository.findById(id);
        ProductOrder updatedProductOrder = null;
        if (fetchedProductOrder.isPresent()) {
            productOrder.setId(id);
            updatedProductOrder = this.repository.save(productOrder);
        }
        return updatedProductOrder;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
