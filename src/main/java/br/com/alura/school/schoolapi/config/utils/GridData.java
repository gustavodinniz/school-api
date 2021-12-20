package br.com.alura.school.schoolapi.config.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GridData<DTO> {

    private Long totalCount;
    private List<DTO> items;

}