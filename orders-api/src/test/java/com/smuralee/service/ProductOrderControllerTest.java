package com.smuralee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smuralee.config.AppConfig;
import com.smuralee.domain.Product;
import com.smuralee.entity.ProductOrder;
import com.smuralee.repository.ProductOrderRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class ProductOrderControllerTest {

    @Spy
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductOrderRepository repository;
    @MockBean
    private AppConfig appConfig;
    @Spy
    private List<ProductOrder> productOrderList;
    @Spy
    private List<Product> productList;

    @BeforeEach
    void setUp() {

        productOrderList = Arrays.asList(
                new ProductOrder(1L, 1L, "Carpet", new BigDecimal("12.00"), "USD"),
                new ProductOrder(2L, 1L, "Lamp", new BigDecimal("11.50"), "USD"),
                new ProductOrder(3L, 2L, "Pillow", new BigDecimal("1.50"), "USD"),
                new ProductOrder(4L, 3L, "Table", new BigDecimal("24.62"), "USD")
        );

        productList = Arrays.asList(
                new Product(1L, 1L, "Carpet", Money.of(new BigDecimal("12.00"), "USD")),
                new Product(2L, 1L, "Lamp", Money.of(new BigDecimal("11.50"), "USD")),
                new Product(3L, 2L, "Pillow", Money.of(new BigDecimal("1.50"), "USD")),
                new Product(4L, 3L, "Table", Money.of(new BigDecimal("24.62"), "USD"))
        );

        when(appConfig.isSecretManagement()).thenReturn(false);

    }

    @Test
    void getAll() throws Exception {

        when(repository.findAll()).thenReturn(productOrderList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(productList))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findAll();

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void getByUserId(final Long selectedId) throws Exception {

        List<ProductOrder> productOrders = productOrderList.stream()
                .filter(order -> order.getUserId().equals(selectedId))
                .collect(Collectors.toList());

        List<Product> products = productList.stream()
                .filter(product -> product.getUserId().equals(selectedId))
                .collect(Collectors.toList());

        when(repository.findByUserId(Mockito.anyLong())).thenReturn(productOrders);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/user/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(products))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findByUserId(Mockito.anyLong());

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void getById(final Long selectedId) throws Exception {

        Optional<ProductOrder> productOrder = productOrderList.stream()
                .filter(o -> o.getId().equals(selectedId))
                .findFirst();

        Optional<Product> product = productList.stream()
                .filter(p -> p.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(productOrder);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(product.orElse(null)))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findById(Mockito.anyLong());

    }

    @Test
    void addProductOrder() throws Exception {

        // Payload for the REST endpoint
        ProductOrder payload = new ProductOrder();
        payload.setAmount(new BigDecimal("23.45"));
        payload.setName("Bicycle");
        payload.setUserId(1001L);
        payload.setCurrencyCode("USD");

        ProductOrder entityResponse = new ProductOrder();
        entityResponse.setId(1L);
        entityResponse.setUserId(1001L);
        entityResponse.setAmount(new BigDecimal("23.45"));
        entityResponse.setCurrencyCode("USD");
        entityResponse.setName("Bicycle");

        // Response for the REST endpoint
        Product response = new Product();
        response.setId(1L);
        response.setCost(Money.of(new BigDecimal("23.45"), "USD"));
        response.setName("Bicycle");
        response.setUserId(1001L);

        when(repository.save(Mockito.any(ProductOrder.class))).thenReturn(entityResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(response))
                );

        // Verify the method is called just once
        verify(repository, times(1)).save(Mockito.any(ProductOrder.class));

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void updateProductOrder(final Long selectedId) throws Exception {

        Optional<ProductOrder> productOrder = productOrderList.stream()
                .filter(order -> order.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(productOrder);

        // Payload for the REST endpoint
        ProductOrder payload = new ProductOrder();
        payload.setAmount(new BigDecimal("20.45"));
        payload.setName("Bicycle Updated");
        payload.setUserId(1001L);
        payload.setCurrencyCode("USD");

        ProductOrder entityResponse = new ProductOrder();
        entityResponse.setId(1L);
        entityResponse.setUserId(1001L);
        entityResponse.setAmount(new BigDecimal("20.45"));
        entityResponse.setCurrencyCode("USD");
        entityResponse.setName("Bicycle Updated");

        // Response for the REST endpoint
        Product response = new Product();
        response.setId(1L);
        response.setCost(Money.of(new BigDecimal("20.45"), "USD"));
        response.setName("Bicycle Updated");
        response.setUserId(1001L);

        when(repository.save(Mockito.any(ProductOrder.class))).thenReturn(entityResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(response))
                );

        // Verify the method is called just once
        verify(repository, times(1)).save(Mockito.any(ProductOrder.class));
        verify(repository, times(1)).findById(Mockito.anyLong());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void deleteById(final Long selectedId) throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        // Verify the method is called just once
        verify(repository, times(1)).deleteById(Mockito.anyLong());

    }
}
