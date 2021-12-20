package br.com.alura.school.schoolapi.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EnrollResponse {

    @JsonProperty
    private final String code;

    @JsonProperty
    private final String username;

    public EnrollResponse(Enroll enroll) {
        this.code = enroll.getCode();
        this.username = enroll.getUsername();
    }

    public EnrollResponse(EnrollResponse enrollResponse) {
        this.code = enrollResponse.code;
        this.username = enrollResponse.username;
    }


}
