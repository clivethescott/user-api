package org.youtap.userapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Component
@Slf4j
class JsonTypicodeApiClient implements UserApiClient {

    private static final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private static final Duration connectionTimeout = Duration.ofSeconds(5);
    private static final Duration readTimeout = Duration.ofSeconds(5);

    private final RestTemplate restTemplate;

    JsonTypicodeApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(connectionTimeout)
                .setReadTimeout(readTimeout)
                .build();
    }

    @Override
    public List<User> getUsers() {
        try {
            User[] users = restTemplate.getForObject(API_URL, User[].class);
            return users == null ? List.of() : List.of(users);
        } catch (RestClientException e) {
            log.warn("Failed to get users", e);
            return List.of();
        }
    }
}
