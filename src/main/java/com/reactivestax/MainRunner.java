package com.reactivestax;

import com.reactivestax.courses.Course;
import com.reactivestax.courses.CourseDAO;
import com.reactivestax.enrollments.EnrollmentDAO;
import com.reactivestax.grades.GradeDAO;
import com.reactivestax.students.Student;
import com.reactivestax.students.StudentDAO;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MainRunner {

    private static final CourseDAO courseDAO = new CourseDAO();
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static final GradeDAO gradeDAO = new GradeDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the School Management System");
        System.out.println("---------------------------------------");

        boolean flag = true;
        while (flag) {
            System.out.println();
            System.out.println("On which you would like to perform operations?");
            System.out.println("1. Course");
            System.out.println("2. Student");
            System.out.println("3. Enroll Student in Course (type Enroll)");
            System.out.println("4. Manage Grades (type Grade)");
            System.out.println("4. Exit");
            System.out.print("Your input >>> ");
            String name = scanner.nextLine();

            switch (name) {
                case "Course" -> System.out.println("""
                        You can perform 4 operations:\s
                        1. Add a course >> Type 'add_course' [name]\s
                        2. Remove a course >> Type 'remove_course' [id]\s
                        3. Update a course >> Type 'update_course' [id] [new name]\s
                        4. Get all courses >> Type 'get_course'
                        """);
                case "Student" -> System.out.println("""
                        You can perform 4 operations:\s
                        1. Add a student >> Type 'add_student' [name]\s
                        2. Remove a student >> Type 'remove_student' [id]\s
                        3. Update a student >> Type 'update_student' [id] [new name]\s
                        4. Get all students >> Type 'get_student'
                        """);
                case "Enroll" -> System.out.println("""
                        You can perform 3 operations:\s
                        1. Enroll a student >> Type 'enroll' [studentId] [courseId]\s
                        2. Remove an enrollment >> Type 'remove_enrollment' [enrollmentId]\s
                        3. Get all enrollments >> Type 'get_enrollment'
                        """);
                case "Grade" -> System.out.println("""
                        You can perform 3 operations:\s
                        1. Add a grade >> Type 'add_grade' [courseId] [studentId] [grade]\s
                        2. Remove a grade >> Type 'remove_grade' [gradeId]\s
                        3. Update a grade >> Type 'update_grade' [courseId] [studentId] [newGrade]
                        """);
                case "Exit" -> flag = false;
                default -> System.out.println("Unknown Command");
            }

            if (flag) {
                System.out.print("Your input >>> ");
                String input = scanner.nextLine();
                String[] command = input.split(" ");

                switch (command[0]) {
                    case "add_course" -> {
                        String courseName = command[1];
                        Set<Student> students = new HashSet<>();
                        for (int i = 2; i < command.length; i++) {
                            int studentId = Integer.parseInt(command[i]);
                            Student student = studentDAO.getStudentById(studentId);
                            if (student != null) {
                                students.add(student);
                            }
                        }
                        courseDAO.addCourse(courseName, students);
                        }
                    case "remove_course" -> courseDAO.removeCourse(Integer.parseInt(command[1]));
                    case "update_course" -> courseDAO.updateCourse(Integer.parseInt(command[1]), command[2]);
                    case "get_course" -> courseDAO.getCourses();
                    case "add_student" -> {
                        String studentName = command[1];
                        Set<Course> courses = new HashSet<>();
                        for (int i = 2; i < command.length; i++) {
                            int courseId = Integer.parseInt(command[i]);
                            Course course = courseDAO.getCourseById(courseId);
                            if (course != null) {
                                courses.add(course);
                            }
                        }
                        studentDAO.addStudent(studentName, courses);
                    }
                    case "remove_student" -> studentDAO.removeStudent(Integer.parseInt(command[1]));
                    case "update_student" -> studentDAO.updateStudent(Integer.parseInt(command[1]), command[2]);
                    case "get_student" -> studentDAO.getStudents();
                    case "enroll" -> {
                        int studentId = Integer.parseInt(command[1]);
                        int courseId = Integer.parseInt(command[2]);
                        enrollmentDAO.addEnrollment(studentId, courseId);
                    }
                    case "remove_enrollment" -> {
                        int enrollmentId = Integer.parseInt(command[1]);
                        enrollmentDAO.removeEnrollment(enrollmentId);
                    }
                    case "get_enrollment" -> enrollmentDAO.getEnrollment();
                    case "add_grade" -> {
                        int courseId = Integer.parseInt(command[1]);
                        int studentId = Integer.parseInt(command[2]);
                        double grade = Double.parseDouble(command[3]);
                        gradeDAO.addGrade(courseId, studentId, grade);
                    }
                    case "remove_grade" -> gradeDAO.removeGrade(Integer.parseInt(command[1]));
                    case "update_grade" -> {
                        int courseId = Integer.parseInt(command[1]);
                        int studentId = Integer.parseInt(command[2]);
                        double newGrade = Double.parseDouble(command[3]);
                        gradeDAO.updateGrade(courseId, studentId, newGrade);
                    }
                    default -> System.out.println("Unknown command");
                }
            }
        }
    }
}
