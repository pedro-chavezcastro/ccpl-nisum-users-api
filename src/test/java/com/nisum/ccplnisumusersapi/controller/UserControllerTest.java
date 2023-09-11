package com.nisum.ccplnisumusersapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.ccplnisumusersapi.DummyMock;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.UpdateUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import com.nisum.ccplnisumusersapi.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private IUserService service;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserTest() throws Exception {
        // Given
        UserDto userDto = DummyMock.getCreateUserRequestDto();
        UserDto userCreatedDto = DummyMock.getCreateUserRequestDto();

        // When
        when(service.createUser(any())).thenReturn(userCreatedDto);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void deleteUserTest() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();

        // When
        mockMvc.perform(delete("/api/v1/users/{user-id}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsersTest() throws Exception {
        // Given
        PageUserDto pageUserDto = DummyMock.getPageUserDto();

        // When
        when(service.getAllUsers(any(), any())).thenReturn(pageUserDto);
        mockMvc.perform(get("/api/v1/users")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total_items").exists())
                .andExpect(jsonPath("$.total_pages").exists())
                .andExpect(jsonPath("$.current_page").exists())
                .andExpect(jsonPath("$.users").isNotEmpty());
    }

    @Test
    void getUserByIdTest() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UserDto createUserRequestDto = DummyMock.getCreateUserRequestDto();

        // When
        when(service.getUserById(any())).thenReturn(createUserRequestDto);
        mockMvc.perform(get("/api/v1/users/{user-id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.phones").isNotEmpty());
    }

    @Test
    void updateUserTest() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UpdateUserDto userDto = DummyMock.getUpdateUserDto();

        // When
        mockMvc.perform(put("/api/v1/users/{user-id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }
}