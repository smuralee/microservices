package com.smuralee.service;

import com.smuralee.config.AppConfig;
import com.smuralee.domain.Product;
import com.smuralee.entity.ProductOrder;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.ProductOrderRepository;
import com.smuralee.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/orders")
public class ProductOrderController {

    private final ProductOrderRepository repository;
    private final AppConfig appConfig;

    public ProductOrderController(ProductOrderRepository repository, AppConfig appConfig) {
        this.repository = repository;
        this.appConfig = appConfig;
    }

    @GetMapping
    public List<Product> getAll() {
        log.info("Getting all the product orders");
        final List<ProductOrder> orders = this.repository.findAll();
        return Utils.getProducts(orders);
    }

    @GetMapping("/config")
    public AppConfig getConfigInfo() {
        log.info("Fetching the config info");
        return this.appConfig;
    }

    @GetMapping("/{id}")
    public Product getById(final @PathVariable Long id) {
        log.info("Getting the product orders by id");
        Optional<ProductOrder> productOrder = this.repository.findById(id);
        if (productOrder.isPresent()) {
            return Utils.getProduct(productOrder.get());
        } else {
            throw new DataNotFoundException();
        }
    }

    @GetMapping("/user/{id}")
    public List<Product> getByUserId(final @PathVariable Long id) {
        log.info("Getting the product orders by user id");
        final List<ProductOrder> orders = this.repository.findByUserId(id);
        return Utils.getProducts(orders);
    }

    @PostMapping
    public Product addProductOrder(final @RequestBody ProductOrder productOrder) {
        log.info("Saving the new product order");
        final ProductOrder order = this.repository.save(productOrder);
        return Utils.getProduct(order);
    }

    @PutMapping("/{id}")
    public Product updateProductOrder(final @RequestBody ProductOrder productOrder, final @PathVariable Long id) {
        log.info("Fetching the product order by id");
        Optional<ProductOrder> fetchedProductOrder = this.repository.findById(id);

        log.info("Updating the product order identified by the id");
        if (fetchedProductOrder.isPresent()) {
            productOrder.setId(id);
            final ProductOrder order = this.repository.save(productOrder);
            return Utils.getProduct(order);
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
