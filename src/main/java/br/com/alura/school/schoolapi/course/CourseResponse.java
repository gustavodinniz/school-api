package br.com.alura.school.schoolapi.course;

import lombok.Data;

import java.util.Optional;

@Data
public class CourseResponse {

    private String code;
    private String name;
    private String shortDescription;

    public CourseResponse(Course course) {
        this.code = course.getCode();
        this.name = course.getName();
        this.shortDescription = Optional.of(course.getDescription()).map(this::abbreviateDescription).orElse("");
    }

    private String abbreviateDescription(String description) {
        if (description.length() <= 13) return description;
        return description.substring(0, 10) + "...";
    }
}
