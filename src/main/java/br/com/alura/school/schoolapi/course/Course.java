package br.com.alura.school.schoolapi.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_course")
public class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @NotBlank
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Size(max = 20)
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    Course(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

}
