package br.com.alura.school.schoolapi.enroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollResponseDTO {

    private String username;
    private Long quantity_enroll;

    public EnrollResponseDTO(EnrollResponseDTO enrolResponseDTO) {
        this.quantity_enroll = enrolResponseDTO.getQuantity_enroll();
        this.username = enrolResponseDTO.getUsername();
    }
}