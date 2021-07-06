package org.youtap.userapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
class UserController {

    private final Supplier<ResponseEntity<User>> userNotFoundResponse = () ->
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(User.notFound());

    private final UserService userService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class InvalidApiUsageException extends RuntimeException {
        InvalidApiUsageException(String message) {
            super(message);
        }
    }

    private ResponseEntity<User> responseOf(Optional<User> user) {
        return user.map(ResponseEntity::ok)
                .orElseGet(userNotFoundResponse);
    }

    @GetMapping("/getusercontacts")
    ResponseEntity<User> getUser(@RequestParam(value = "userId", required = false) Integer userId,
                           @RequestParam(value = "userName", required = false) String userName) {

        if (userId == null && userName == null) {
            throw new InvalidApiUsageException("Provide either userId or userName");
        }

        if (userId == null) {
            return responseOf(userService.getUserByName(userName));
        } else {
            return responseOf(userService.getUserById(userId));
        }
    }
}
