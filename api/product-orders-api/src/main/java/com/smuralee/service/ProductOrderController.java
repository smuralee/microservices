package com.smuralee.service;

import com.smuralee.entity.InstanceInfo;
import com.smuralee.entity.ProductOrder;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/product-orders")
public class ProductOrderController {

    private final ProductOrderRepository repository;

    public ProductOrderController(ProductOrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProductOrder> getAll() {
        log.info("Getting all the product orders");
        return this.repository.findAll();
    }

    @GetMapping("/info")
    public InstanceInfo getInfo() throws IOException {
        log.info("Fetching the instance info");
        InstanceInfo instanceInfo = new InstanceInfo();
        InetAddress localhost = InetAddress.getLocalHost();

        instanceInfo.setHostIpAddress(localhost.getHostAddress());
        instanceInfo.setHostname(localhost.getHostName());

        URL url = new URL("http://bot.whatismyipaddress.com");
        BufferedReader sc = new BufferedReader(new InputStreamReader(url.openStream()));
        instanceInfo.setPublicIpAddress(sc.readLine().trim());

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
