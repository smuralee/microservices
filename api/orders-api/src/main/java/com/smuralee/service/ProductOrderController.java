package com.smuralee.service;

import com.smuralee.config.AppConfig;
import com.smuralee.entity.InstanceInfo;
import com.smuralee.entity.ProductOrder;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
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
    public List<ProductOrder> getAll() {
        log.info("Getting all the product orders");
        return this.repository.findAll();
    }

    @GetMapping("/config")
    public AppConfig getConfigInfo() {
        log.info("Fetching the config info");
        return this.appConfig;
    }

    @GetMapping("/info")
    public InstanceInfo getInstanceInfo() throws IOException {
        log.info("Fetching the instance info");
        InstanceInfo instanceInfo = new InstanceInfo();
        InetAddress localhost = InetAddress.getLocalHost();

        instanceInfo.setHostIpAddress(localhost.getHostAddress());
        instanceInfo.setHostname(localhost.getHostName());

        return instanceInfo;
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
