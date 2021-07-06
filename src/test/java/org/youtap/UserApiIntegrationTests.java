package org.youtap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.youtap.userapi.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("User API integration tests")
class UserApiIntegrationTests {

    private static final String API_URL = "/getusercontacts";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("getting an existing user's contacts by name works")
    void getExistingUserDetailsByName() {
        final var userName = "Bret";
        final String userByNameUrl = API_URL + "?userName=" + userName;
        final var phone = "1-770-736-8031 x56442";
        final var email = "Sincere@april.biz";
        final var id = 1;

        final User expectedUser = new User(id, email, phone, "Bret");
        final User actualUser = restTemplate.getForObject(userByNameUrl, User.class);

        assertThat(actualUser.getId())
                .as("Expected user ID to be %d but was %d", id, actualUser.getId())
                .isEqualTo(expectedUser.getId());
        assertThat(actualUser.getEmail())
                .as("Expected user email to be %s but was %s", email, actualUser.getEmail())
                .isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.getPhone())
                .as("Expected user phone to be %s but was %s", phone, actualUser.getPhone())
                .isEqualTo(expectedUser.getPhone());
    }

    @Test
    @DisplayName("getting an existing user's contacts by ID works")
    void getExistingUserDetailsByID() {
        final var userId = 1;
        final String userByIdUrl = API_URL + "?userId=" + userId;
        final var phone = "1-770-736-8031 x56442";
        final var email = "Sincere@april.biz";

        final User expectedUser = new User(userId, email, phone, "Bret");
        final User actualUser = restTemplate.getForObject(userByIdUrl, User.class);

        assertThat(actualUser.getId())
                .as("Expected user ID to be %d but was %d", userId, actualUser.getId())
                .isEqualTo(expectedUser.getId());
        assertThat(actualUser.getEmail())
                .as("Expected user email to be %s but was %s", email, actualUser.getEmail())
                .isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.getPhone())
                .as("Expected user phone to be %s but was %s", phone, actualUser.getPhone())
                .isEqualTo(expectedUser.getPhone());
    }

    @Test
    @DisplayName("getting an non-existing user's contacts works")
    void getNonExistingUserDetails() {
        final var userName = "0000-AX234234-%%%";
        final String userByNameUrl = API_URL + "?userName=" + userName;

        final User expectedUser = new User(-1, null, null, null);
        final User actualUser = restTemplate.getForObject(userByNameUrl, User.class);

        assertThat(actualUser.getId())
                .as("Expected user ID to be -1 but was %d", actualUser.getId())
                .isEqualTo(expectedUser.getId());
        assertThat(actualUser.getEmail())
                .as("Expected user email to be null but was %s", actualUser.getEmail())
                .isNull();
        assertThat(actualUser.getPhone())
                .as("Expected user phone to be null but was %s", actualUser.getPhone())
                .isNull();
    }
}
