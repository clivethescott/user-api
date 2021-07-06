package org.youtap.userapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@DisplayName("User JSON tests")
class UserJsonTest {

    @Autowired
    private JacksonTester<User> jsonTester;

    @Test
    @DisplayName("given a user serialization generates correct JSON")
    void serializeTest() throws IOException {
        var email = "test@gmail.com";
        var phone = "111-234";
        var userId = 1;
        final var user = new User(userId, email, phone, "rootuser");
        final JsonContent<User> json = jsonTester.write(user);

        assertThat(json)
                .hasJsonPathNumberValue("$.id", userId)
                .hasJsonPathStringValue("$.email", email)
                .hasJsonPathStringValue("$.phone", phone)
                .hasEmptyJsonPathValue("$.userName");

    }
}