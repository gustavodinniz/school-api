package br.com.alura.school.schoolapi.course;

import br.com.alura.school.schoolapi.config.exception.NotFoundException;
import br.com.alura.school.schoolapi.config.exception.ValidationException;
import br.com.alura.school.schoolapi.config.utils.GridData;
import br.com.alura.school.schoolapi.enroll.*;
import br.com.alura.school.schoolapi.user.User;
import br.com.alura.school.schoolapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollRepository enrollRepository;

    @GetMapping
    List<CourseResponse> allCourses() {
        log.info("Fetching All Courses");
        return courseRepository
                .findAll()
                .stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    ResponseEntity<CourseResponse> courseByCode(@PathVariable("code") String code) {
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(format("Course with code %s not found", code)));
        log.info("Fetching Course by code: " + code);
        return ResponseEntity.ok(new CourseResponse(course));
    }

    @PostMapping
    ResponseEntity<Void> newCourse(@RequestBody @Valid NewCourseRequest newCourseRequest) {
        courseRepository.save(newCourseRequest.toEntity());
        URI location = URI.create(format("/courses/%s", newCourseRequest.getCode()));
        log.info("Created new Course: " + newCourseRequest.getName() + " with code: " + newCourseRequest.getCode());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{courseCode}/enroll")
    ResponseEntity<EnrollResponse> newEnrollByCourseCode(
            @PathVariable("courseCode") String code,
            @RequestBody @Valid NewEnrollRequest newEnrollRequest) {

        Course course = getCourse(code);
        var courseCode = course.getCode();
        User user = getUser(newEnrollRequest);
        var username = newEnrollRequest.getUsername();

        validatedUserEnrollCourse(code, newEnrollRequest);
        saveEnroll(newEnrollRequest, courseCode, username);
        log.info("Created Enroll with User: " + user.getUsername() + " and Course Code: " + courseCode);
        URI location = URI.create(format("/courses/%s/enroll", courseCode));
        return ResponseEntity.created(location).build();
    }

    private void validatedUserEnrollCourse(String code, NewEnrollRequest newEnrollRequest) {
        Optional<Enroll> enrollByCodeAndUsername = enrollRepository.findByCodeAndUsername(code, newEnrollRequest.getUsername());
        if (enrollByCodeAndUsername.isPresent()) {
            throw new ValidationException(format("User is already enrolled in the course.", newEnrollRequest.getUsername()));
        }
    }

    private void saveEnroll(NewEnrollRequest newEnrollRequest, String courseCode, String username) {
        enrollRepository.save(newEnrollRequest.toEntity(courseCode, username));
    }

    private User getUser(NewEnrollRequest newEnrollRequest) {
        return userRepository.findByUsername(newEnrollRequest.getUsername())
                .orElseThrow(() -> new NotFoundException(format("User %s not found", newEnrollRequest.getUsername())));
    }

    private Course getCourse(String code) {
        return courseRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(format("Course with code %s not found", code)));
    }

    @GetMapping("/enroll/report")
    public ResponseEntity<GridData<EnrollResponseDTO>> findEnrollReport() {
        log.info("Fetching Enroll Report");
        GridData<EnrollResponseDTO> list = enrollRepository.findEnrollReport();
        return ResponseEntity.ok(list);
    }
}
