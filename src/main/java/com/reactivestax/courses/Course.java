package com.reactivestax.courses;

import com.reactivestax.students.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "course")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "course_name")
    private String courseName;

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private Set<Student> students;
}
