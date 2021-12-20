package br.com.alura.school.schoolapi.enroll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EnrollRepository extends JpaRepository<Enroll, Long>, JpaSpecificationExecutor<Enroll>, EnrollRepositoryCustom {

    Optional<Enroll> findByCodeAndUsername(String code, String username);

    @Query(value = "select count(code) as code, username" +
            " from t_enroll" +
            " group by username" +
            " order by code desc", nativeQuery = true)
    List<EnrollResponseDTO> countEnrollsAndUsername();

}
