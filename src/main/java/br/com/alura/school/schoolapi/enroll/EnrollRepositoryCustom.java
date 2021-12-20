package br.com.alura.school.schoolapi.enroll;

import br.com.alura.school.schoolapi.config.utils.GridData;

public interface EnrollRepositoryCustom {
    GridData<EnrollResponseDTO> findEnrollReport();

}
