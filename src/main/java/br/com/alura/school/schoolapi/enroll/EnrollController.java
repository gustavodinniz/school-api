package br.com.alura.school.schoolapi.enroll;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping("enrolls")
public class EnrollController {

    private final EnrollRepository enrollRepository;

    public EnrollController(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

    @GetMapping
    List<EnrollResponse> allEnrolls() {
        log.info("Fecthing all Enrolls");
        return enrollRepository
                .findAll()
                .stream()
                .map(EnrollResponse::new)
                .collect(Collectors.toList());
    }
}