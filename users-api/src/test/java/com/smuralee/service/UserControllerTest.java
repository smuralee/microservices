package com.smuralee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smuralee.config.AppConfig;
import com.smuralee.entity.User;
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private AppConfig appConfig;

    @Spy
    private List<User> userList;

    @Spy
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

        userList = Arrays.asList(
                new User(1L, "Adam", 25),
                new User(2L, "Jamie", 45),
                new User(3L, "Raj", 36),
                new User(4L, "Steve", 41)
        );

        when(appConfig.isSecretManagement()).thenReturn(false);

    }

    @Test
    void getAll() throws Exception {

        when(repository.findAll()).thenReturn(userList);

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

        Optional<User> user = userList.stream()
                .filter(person -> person.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(user);

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
        User payload = new User();
        payload.setName("Jacob");
        payload.setAge(35);

        // Response for the REST endpoint
        User response = new User();
        response.setId(1L);
        response.setName("Jacob");
        response.setAge(35);

        when(repository.save(Mockito.any(User.class))).thenReturn(response);

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
        verify(repository, times(1)).save(Mockito.any(User.class));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void updateUser(final Long selectedId) throws Exception {

        Optional<User> user = userList.stream()
                .filter(person -> person.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(user);

        // Payload for the REST endpoint
        User payload = new User();
        payload.setName("Jacob Doe");
        payload.setAge(35);

        // Response for the REST endpoint
        User response = new User();
        response.setId(1L);
        response.setName("Jacob Doe");
        response.setAge(35);

        when(repository.save(Mockito.any(User.class))).thenReturn(response);

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
        verify(repository, times(1)).save(Mockito.any(User.class));
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
