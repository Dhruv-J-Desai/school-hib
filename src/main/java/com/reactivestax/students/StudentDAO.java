package com.reactivestax.students;

import com.reactivestax.courses.Course;
import com.reactivestax.enrollments.Enrollment;
import com.reactivestax.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class StudentDAO {

    public void addStudent(String studentName, Set<Course> courses){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = new Student();

            student.setStudentName(studentName);

            session.persist(student);

            for (Course course : courses) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                session.persist(enrollment);
            }

            transaction.commit();
            System.out.println("Student '" + studentName + "' added.");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public void updateStudent(int studentId,String studentName){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                student.setStudentName(studentName);
                session.update(student);
                transaction.commit();
                System.out.println("Student ID '" + studentId + "' updated.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void removeStudent(int studentId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.remove(student);
                transaction.commit();
                System.out.println("Student ID '" + studentId + "' removed.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing student: " + e.getMessage());
        }
    }

    public Student getStudentById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student student = null;

        try {
            student = session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return student;
    }

    public void getStudents(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> students = session.createQuery("from Student", Student.class).list();
            for (Student student : students) {
                System.out.println(student.getStudentName());
            }
        } catch (Exception e) {
            System.out.println("Error getting students: " + e.getMessage());
        }
    }
}
