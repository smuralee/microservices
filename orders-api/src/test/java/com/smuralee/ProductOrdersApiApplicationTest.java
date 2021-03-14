package com.smuralee;

import com.smuralee.config.AppConfig;
import com.smuralee.service.ProductOrderController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductOrdersApiApplicationTest {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ProductOrderController controller;

    @Test
    void contextLoads() {
        appConfig.setRdsSecret("dev/rds/api/orders");
        assertThat(controller).isNotNull();
        assertThat(appConfig).isNotNull();
    }

}
