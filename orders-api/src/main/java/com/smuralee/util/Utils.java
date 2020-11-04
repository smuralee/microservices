package com.smuralee.util;

import com.smuralee.domain.Product;
import com.smuralee.entity.ProductOrder;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    /**
     * <p>
     * Transforms order list to product list
     * </p>
     *
     * @param orders
     * @return List<Product>
     */
    public static List<Product> getProducts(final List<ProductOrder> orders) {

        return orders.stream().map(order -> {
            Product product = new Product();
            product.setId(order.getId());
            product.setUserId(order.getUserId());
            product.setName(order.getName());
            product.setCost(Money.of(order.getAmount(), order.getCurrencyCode()));
            return product;
        }).collect(Collectors.toList());
    }

    /**
     * <p>
     * Transforms order to product
     * </p>
     *
     * @param order
     * @return Product
     */
    public static Product getProduct(final ProductOrder order) {
        Product product = new Product();
        product.setId(order.getId());
        product.setUserId(order.getUserId());
        product.setName(order.getName());
        product.setCost(Money.of(order.getAmount(), Monetary.getCurrency(order.getCurrencyCode())));
        return product;
    }
}
