package com.smuralee.util;

import com.smuralee.domain.Product;
import com.smuralee.entity.ProductOrder;
import org.javamoney.moneta.Money;

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
        return orders.stream().map(Utils::getProductObject).collect(Collectors.toList());
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
        return getProductObject(order);
    }

    /**
     * Get Product domain object
     *
     * @param order
     * @return Product
     */
    private static Product getProductObject(ProductOrder order) {
        Product product = new Product();
        product.setId(order.getId());
        product.setUserId(order.getUserId());
        product.setName(order.getName());
        product.setCost(Money.of(order.getAmount(), order.getCurrencyCode()));
        return product;
    }
}
