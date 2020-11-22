package com.smuralee.client;

import com.smuralee.domain.Order;

import java.util.List;

public interface IOrderClient {

    List<Order> getOrdersByUserId(final Long id);

    String getHostname();
}
