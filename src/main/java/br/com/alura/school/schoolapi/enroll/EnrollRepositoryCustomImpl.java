package br.com.alura.school.schoolapi.enroll;

import br.com.alura.school.schoolapi.config.utils.GridData;
import br.com.alura.school.schoolapi.config.utils.NativeBaseRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollRepositoryCustomImpl extends NativeBaseRepository implements EnrollRepositoryCustom {

    @Resource(name = "local-db")
    protected DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    public GridData<EnrollResponseDTO> findEnrollReport() {
        Map<String, Object> params = new HashMap<>();

        List<EnrollResponseDTO> list = parseNativeQuery(
                "select count(code) as quantity_enroll, username as username\n" +
                        "from t_enroll\n" +
                        "group by username\n" +
                        "order by quantity_enroll desc;\n",
                EnrollResponseDTO.class,
                params
        );

        return GridData.<EnrollResponseDTO>builder()
                .totalCount((long) list.size())
                .items(list)
                .build();
    }
}
