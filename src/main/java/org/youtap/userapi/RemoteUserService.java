package org.youtap.userapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
class RemoteUserService implements UserService {

    private final UserApiClient client;

    private Optional<User> getUser(Supplier<List<User>> users, Predicate<User> condition) {
        return users.get().stream()
                .filter(condition)
                .findAny();
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        if (userId == null) {
            return Optional.empty();
        }
        final Predicate<User> matchingUserId = user -> user.getId() == userId;
        return getUser(client::getUsers, matchingUserId);
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        if (!StringUtils.hasText(userName)) {
            return Optional.empty();
        }
        final Predicate<User> matchingUserName = user -> userName.equals(user.getUserName());
        return getUser(client::getUsers, matchingUserName);
    }
}
