package com.smuralee;

import com.smuralee.config.AppConfig;
import com.smuralee.config.model.RDSSecret;
import com.smuralee.service.ProductOrderController;
import com.smuralee.util.SecretsClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductOrdersApiApplicationTest {

    @Spy
    private static final AppConfig appConfig = new AppConfig();
    @Spy
    private static final SecretsClient secretsClient = new SecretsClient(appConfig);
    @MockBean
    private ProductOrderController controller;

    @BeforeAll
    static void setUp() {
        RDSSecret rdsSecret = new RDSSecret();
        when(secretsClient.getSecret()).thenReturn(rdsSecret);
    }

    @Test
    void contextLoads() {

        assertThat(controller).isNotNull();
        assertThat(appConfig).isNotNull();
    }

}
