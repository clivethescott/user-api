package org.youtap.userapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(JsonTypicodeApiClient.class)
class JsonTypicodeApiClientTest {

    @Autowired
    JsonTypicodeApiClient client;

    @Autowired
    MockRestServiceServer server;

    @Test
    @DisplayName("gets correct user details from returned JSON")
    void testGetUserDetailsFromJson() {
        final int userId = 2;
        final var usersApiUrl = "https://jsonplaceholder.typicode.com/users";

        final String userJson = "[{\"id\": 2, \"email\": \"joe@facebook.tv\", \"phone\": \"010-111\", \"username\": \"test\"}]";
        this.server.expect(requestTo(usersApiUrl))
                .andRespond(withSuccess(userJson, MediaType.APPLICATION_JSON));

        User user = this.client.getUsers().get(0);

        Assertions.assertThat(user.getId()).isEqualTo(userId);
        Assertions.assertThat(user.getEmail()).isEqualTo("joe@facebook.tv");
        Assertions.assertThat(user.getPhone()).isEqualTo("010-111");
        Assertions.assertThat(user.getUserName()).isEqualTo("test");
    }
}