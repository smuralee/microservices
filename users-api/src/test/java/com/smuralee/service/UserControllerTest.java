package com.smuralee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smuralee.config.AppConfig;
import com.smuralee.domain.Name;
import com.smuralee.domain.Order;
import com.smuralee.domain.User;
import com.smuralee.entity.UserInfo;
import com.smuralee.repository.UserRepository;
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
class UserControllerTest {

    @Spy
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository repository;
    @MockBean
    private AppConfig appConfig;
    @Spy
    private List<UserInfo> userInfoList;
    @Spy
    private List<User> userList;
    @Spy
    private List<Order> ordersList;

    @BeforeEach
    void setUp() {

        userInfoList = Arrays.asList(
                new UserInfo(1L, "Adam", "J", "Driver", 25),
                new UserInfo(2L, "Jamie", "H", "Lawrence", 45),
                new UserInfo(3L, "Raj", "J", "Koothrappali", 36),
                new UserInfo(4L, "Steve", "H", "Smith", 41)
        );

        ordersList = Arrays.asList(
                new Order(1L, "Carpet", "USD 12.00"),
                new Order(2L, "Lamp", "USD 11.50"),
                new Order(3L, "Pillow", "USD 1.50")
        );

        userList = Arrays.asList(
                new User(1L, new Name("Adam", "J", "Driver"), 25, null),
                new User(2L, new Name("Jamie", "H", "Lawrence"), 45, null),
                new User(3L, new Name("Raj", "J", "Koothrappali"), 36, null),
                new User(4L, new Name("Steve", "H", "Smith"), 41, null)
        );

    }

    @Test
    void getAll() throws Exception {

        when(repository.findAll()).thenReturn(userInfoList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(userList))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findAll();
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void getById(final Long selectedId) throws Exception {

        Optional<UserInfo> userInfo = userInfoList.stream()
                .filter(person -> person.getId().equals(selectedId))
                .findFirst();

        Optional<User> user = userList.stream()
                .filter(person -> person.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(userInfo);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(user.orElse(null)))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findById(Mockito.anyLong());

    }

    @Test
    void addUser() throws Exception {

        // Payload for the REST endpoint
        UserInfo payload = new UserInfo();
        payload.setFirstName("Jacob");
        payload.setLastName("Smith");
        payload.setAge(35);

        UserInfo entityResponse = new UserInfo();
        entityResponse.setId(1L);
        entityResponse.setFirstName("Jacob");
        entityResponse.setLastName("Smith");
        entityResponse.setAge(35);

        // Response for the REST endpoint
        User response = new User();
        response.setId(1L);
        response.setName(new Name("Jacob", null, "Smith"));
        response.setAge(35);

        when(repository.save(Mockito.any(UserInfo.class))).thenReturn(entityResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/users/")
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
        verify(repository, times(1)).save(Mockito.any(UserInfo.class));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void updateUser(final Long selectedId) throws Exception {

        Optional<UserInfo> user = userInfoList.stream()
                .filter(person -> person.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(user);

        // Payload for the REST endpoint
        UserInfo payload = new UserInfo();
        payload.setFirstName("Jacob");
        payload.setLastName("Smith");
        payload.setAge(35);

        UserInfo entityResponse = new UserInfo();
        entityResponse.setId(1L);
        entityResponse.setFirstName("Jacob");
        entityResponse.setLastName("Smith");
        entityResponse.setAge(35);

        // Response for the REST endpoint
        User response = new User();
        response.setId(1L);
        response.setName(new Name("Jacob", null, "Smith"));
        response.setAge(35);

        when(repository.save(Mockito.any(UserInfo.class))).thenReturn(entityResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/users/".concat(String.valueOf(selectedId)))
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
        verify(repository, times(1)).save(Mockito.any(UserInfo.class));
        verify(repository, times(1)).findById(Mockito.anyLong());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void deleteById(final Long selectedId) throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/".concat(String.valueOf(selectedId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        // Verify the method is called just once
        verify(repository, times(1)).deleteById(Mockito.anyLong());

    }
}
