package com.smuralee.client;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.smuralee.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@XRayEnabled
@Service
public class OrderClient implements IOrderClient {

    private final Environment environment;

    private final RestTemplate restTemplate;

    public OrderClient(Environment environment, RestTemplate restTemplate) {
        this.environment = environment;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Order> getOrdersByUserId(Long id) {

        String endpoint = this.getHostname() + "/orders/user/" + id;
        log.info("Connecting to : " + endpoint);

        ResponseEntity<List<Order>> rateResponse =
                restTemplate.exchange(
                        endpoint,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        return rateResponse.getBody();
    }

    @Override
    public String getHostname() {
        return "http://" + this.environment.getProperty("ORDERS_HOST");
    }
}
