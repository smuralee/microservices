package com.smuralee.service;

import com.smuralee.entity.ProductOrder;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/product-orders")
public class ProductOrderController {

    @Autowired
    private ProductOrderRepository repository;

    @GetMapping
    public List<ProductOrder> getAll() {
        log.info("Getting all the product orders");
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ProductOrder getById(final @PathVariable Long id) {
        log.info("Getting the product orders by id");
        Optional<ProductOrder> productOrder = this.repository.findById(id);
        return productOrder.orElseThrow(DataNotFoundException::new);
    }

    @PostMapping
    public ProductOrder addProductOrder(final @RequestBody ProductOrder productOrder) {
        log.info("Saving the new product order");
        return this.repository.save(productOrder);
    }

    @PutMapping("/{id}")
    public ProductOrder updateProductOrder(final @RequestBody ProductOrder productOrder, final @PathVariable Long id) {
        log.info("Fetching the product order by id");
        Optional<ProductOrder> fetchedProductOrder = this.repository.findById(id);

        log.info("Updating the product order identified by the id");
        if (fetchedProductOrder.isPresent()) {
            productOrder.setId(id);
            return this.repository.save(productOrder);
        } else {
            throw new DataNotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting the product order by id");
        this.repository.deleteById(id);
    }
}