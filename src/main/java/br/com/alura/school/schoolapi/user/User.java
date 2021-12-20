package br.com.alura.school.schoolapi.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @NotBlank
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
