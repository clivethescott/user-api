package org.youtap.userapi;

import java.util.Optional;

interface UserService {
    Optional<User> getUserById(Integer userId);
    Optional<User> getUserByName(String userName);
}
