package com.reactivestax.grades;

import com.reactivestax.courses.Course;
import com.reactivestax.students.Student;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Grades")
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "grade", nullable = false)
    private double grade;

    public Grade() {}

    public Grade(Course course, Student student, double grade) {
        this.course = course;
        this.student = student;
        this.grade = grade;
    }
}
