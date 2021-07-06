package org.youtap.userapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "email", "phone"})
public class User {
    private int id;
    private String email;
    private String phone;

    @JsonProperty(value = "username", access = JsonProperty.Access.WRITE_ONLY)
    private String userName;

    private User(int id) {
        this.id = id;
    }

    public User(int id, String email, String phone, String userName) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
    }

    static User notFound() {
        return new User(-1);
    }

}
