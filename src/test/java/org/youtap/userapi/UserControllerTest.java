package org.youtap.userapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("User controller tests")
class UserControllerTest {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String API_URL = "/getusercontacts";

    @MockBean
    UserService service;

    @Autowired
    MockMvc mockMvc;

    private MockHttpServletRequestBuilder getUserById(int id) {
        return get(API_URL + "?userId={id}", id);
    }

    @Test
    @DisplayName("given an existing user, we can get details by ID")
    void testGetUserById() throws Exception {
        var userId = 1;
        var email = "tom@gmail.com";
        var phone = "+263-772-8888";
        var userName = "admin";

        final var user = new User(userId, email, phone, userName);
        given(service.getUserById(anyInt())).willReturn(Optional.of(user));

        mockMvc.perform(getUserById(userId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", CONTENT_TYPE_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.phone").value(phone));
    }

    private MockHttpServletRequestBuilder getUserByName(String name) {
        return get(API_URL + "?userName={name}", name);
    }

    @Test
    @DisplayName("given an existing user, we can get details by name")
    void testGetUserByName() throws Exception {
        var userId = 1;
        var email = "tom@gmail.com";
        var phone = "+263-772-8888";
        var userName = "admin2";

        final var user = new User(userId, email, phone, userName);
        given(service.getUserByName(anyString())).willReturn(Optional.of(user));

        mockMvc.perform(getUserByName("test"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", CONTENT_TYPE_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.phone").value(phone));
    }

    @Test
    @DisplayName("given a non-existing user, we can don't details by name")
    void testGetNonExistingUserByName() throws Exception {
        var userId = -1;

        given(service.getUserByName(anyString())).willReturn(Optional.empty());

        mockMvc.perform(getUserByName("test"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", CONTENT_TYPE_JSON))
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    @DisplayName("given no ID and no name we get a bad request")
    void testGetUserNoIdAndName() throws Exception {

        mockMvc.perform(get(API_URL))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(service);
    }
}