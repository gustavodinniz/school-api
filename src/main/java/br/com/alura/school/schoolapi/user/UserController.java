package br.com.alura.school.schoolapi.user;

import br.com.alura.school.schoolapi.config.exception.NotFoundException;
import br.com.alura.school.schoolapi.config.exception.ValidationException;
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
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    List<UserResponse> allUsers() {
        log.info("Fetching all Users");
        return userRepository
                .findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    ResponseEntity<UserResponse> userByUsername(@PathVariable("username") String username) {
        User user = userRepository
                .findByUsername(username).orElseThrow(() -> new NotFoundException(format("User %s not found", username)));
        log.info("Fetching User: " + username);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping
    ResponseEntity<Void> newUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        validatedUsernameAndEmail(newUserRequest);
        userRepository.save(newUserRequest.toEntity());
        URI location = URI.create(format("/users/%s", newUserRequest.getUsername()));
        log.info("Created new User: " + newUserRequest.getUsername());
        return ResponseEntity.created(location).build();
    }

    private void validatedUsernameAndEmail(NewUserRequest newUserRequest) {
        Optional<User> findByUsername = userRepository.findByUsername(newUserRequest.getUsername());
        Optional<User> findByEmail = userRepository.findByEmail(newUserRequest.getEmail());

        if(findByUsername.isPresent()){
            throw new ValidationException(format("There is already a user with this username: " + newUserRequest.getUsername()));
        }
        if (findByEmail.isPresent()){
            throw new ValidationException(format("There is already a user with this email: " + newUserRequest.getEmail()));
        }

        if(newUserRequest.getUsername() == null || newUserRequest.getUsername().isEmpty()){
            throw new ValidationException(format("Username must be informed: "));
        }

        if(newUserRequest.getEmail() == null || newUserRequest.getEmail().isEmpty()){
            throw new ValidationException(format("Email must be informed: "));
        }

    }
}
