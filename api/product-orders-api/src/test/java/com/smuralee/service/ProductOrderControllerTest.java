package com.smuralee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Spy
    private List<ProductOrder> productOrderList;

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

        productOrderList = Arrays.asList(
                new ProductOrder(1L, "Carpet", "£12.00"),
                new ProductOrder(2L, "Lamp", "£11.50"),
                new ProductOrder(3L, "Pillow", "£1.50"),
                new ProductOrder(4L, "Table", "£24.62")
        );

    }

    @Test
    void getAll() throws Exception {

        when(repository.findAll()).thenReturn(productOrderList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/product-orders")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
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
    @ValueSource(longs = {1, 2, 3, 4})
    void getById(final Long selectedId) throws Exception {

        Optional<ProductOrder> productOrder = productOrderList.stream()
                .filter(order -> order.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(productOrder);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/product-orders/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(productOrder.get()))
                );

        verify(repository, times(1)).findById(Mockito.anyLong());

    }

    @Test
    void addProductOrder() {
    }

    @Test
    void updateProductOrder() {
    }

    @Test
    void deleteById() {
    }
}