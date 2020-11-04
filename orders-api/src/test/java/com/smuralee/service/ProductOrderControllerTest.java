package com.smuralee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smuralee.config.AppConfig;
import com.smuralee.entity.ProductOrder;
import com.smuralee.repository.ProductOrderRepository;
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
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class ProductOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductOrderRepository repository;

    @MockBean
    private AppConfig appConfig;

    @Spy
    private List<ProductOrder> productOrderList;

    @Spy
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

        productOrderList = Arrays.asList(
                new ProductOrder(1L, 1L, "Carpet", new BigDecimal("12.00"), "USD"),
                new ProductOrder(2L, 1L, "Lamp", new BigDecimal("11.50"), "USD"),
                new ProductOrder(3L, 2L, "Pillow", new BigDecimal("1.50"), "USD"),
                new ProductOrder(4L, 3L, "Table", new BigDecimal("24.62"), "USD")
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
                                .json(this.mapper.writeValueAsString(productOrderList))
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

        when(repository.findByUserId(Mockito.anyLong())).thenReturn(productOrders);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/user/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(productOrders))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findByUserId(Mockito.anyLong());

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void getById(final Long selectedId) throws Exception {

        Optional<ProductOrder> productOrder = productOrderList.stream()
                .filter(order -> order.getId().equals(selectedId))
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
                                .json(this.mapper.writeValueAsString(productOrder.orElse(null)))
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

        // Response for the REST endpoint
        ProductOrder response = new ProductOrder();
        response.setId(1L);
        response.setAmount(new BigDecimal("23.45"));
        response.setName("Bicycle");
        payload.setUserId(1001L);
        payload.setCurrencyCode("USD");

        when(repository.save(Mockito.any(ProductOrder.class))).thenReturn(response);

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

        // Response for the REST endpoint
        ProductOrder response = new ProductOrder();
        response.setId(selectedId);
        response.setAmount(new BigDecimal("20.45"));
        response.setName("Bicycle Updated");
        response.setUserId(1001L);
        response.setCurrencyCode("USD");

        when(repository.save(Mockito.any(ProductOrder.class))).thenReturn(response);

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
