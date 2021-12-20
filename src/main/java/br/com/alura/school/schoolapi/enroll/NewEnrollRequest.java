package br.com.alura.school.schoolapi.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewEnrollRequest {

    @JsonProperty
    private final String code;

    @Size(max = 20)
    @NotBlank
    @JsonProperty
    private final String username;

    public NewEnrollRequest(String code, String username) {
        this.code = code;
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public Enroll toEntity(String code, String username) {
        return new Enroll(code, username);
    }
}
