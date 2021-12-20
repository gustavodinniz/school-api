package br.com.alura.school.schoolapi.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponse {

    @JsonProperty
    private final String username;

    @JsonProperty
    private final String email;

    UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
