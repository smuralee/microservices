package com.smuralee;

import com.smuralee.config.AppConfig;
import com.smuralee.service.TodoController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TodosApiApplicationTest {

    @Autowired
    private TodoController controller;

    @Autowired
    private AppConfig appConfig;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(appConfig).isNotNull();
    }
}