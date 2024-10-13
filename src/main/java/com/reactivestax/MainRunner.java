package com.reactivestax;

import com.reactivestax.courses.Course;
import com.reactivestax.students.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

public class MainRunner {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session= sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        Course course1 = new Course();
        course1.setCourseName("Java");

        Student student1 = new Student();
        student1.setStudentName("Dhruv");

        Student student2 = new Student();
        student2.setStudentName("Dev");

        Set<Course> courses = new HashSet<>();
        courses.add(course1);

        student1.setCourses(courses);
        student2.setCourses(courses);

        Set<Student> students= new HashSet<>();
        students.add(student1);
        students.add(student2);

        course1.setStudents(students);

        session.persist(course1);

        session.persist(student1);
        session.persist(student2);

        transaction.commit();

        session.close();
        sessionFactory.close();
    }
}
