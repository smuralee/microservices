package com.smuralee.client;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.smuralee.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@XRayEnabled
@Service
public class OrderClient implements IOrderClient {

    private final RestTemplate restTemplate;

    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Order> getOrdersByUserId(Long id) {

        StringBuilder endpoint = new StringBuilder("http://orders-api:8080/orders/user/").append(id);
        log.info("Connecting to : " + endpoint.toString());

        ResponseEntity<List<Order>> rateResponse =
                restTemplate.exchange(
                        endpoint.toString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        return rateResponse.getBody();
    }
}
