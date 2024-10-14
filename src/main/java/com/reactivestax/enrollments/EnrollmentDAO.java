package com.reactivestax.enrollments;

import com.reactivestax.courses.Course;
import com.reactivestax.students.Student;
import com.reactivestax.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EnrollmentDAO {
        public void addEnrollment(int studentId, int courseId){
            Transaction transaction = null;

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                Student student = session.get(Student.class, studentId);
                Course course = session.get(Course.class, courseId);

                if (student != null && course != null) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setStudent(student);
                    enrollment.setCourse(course);

                    session.persist(enrollment);
                    transaction.commit();
                    System.out.println("Student with ID '" + studentId + "' has been enrolled in Course with ID '" + courseId + "'.");
                } else {
                    System.out.println("Student or Course not found!");
                }

            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Error adding enrollment: " + e.getMessage());
            }
    }

    public void removeEnrollment(int enrollmentId){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Enrollment enrollment = session.get(Enrollment.class, enrollmentId);

            if (enrollment != null) {
                session.remove(enrollment);
                transaction.commit();
                System.out.println("Enrollment with ID '" + enrollmentId + "' has been removed.");
            } else {
                System.out.println("Enrollment not found.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error removing enrollment: " + e.getMessage());
        }
    }

    public void getEnrollment(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Enrollment> enrollments = session.createQuery("from Enrollment", Enrollment.class).list();

            for (Enrollment enrollment : enrollments) {
                System.out.print("Enrollment ID: " + enrollment.getId() + " ");
                System.out.print("Course ID: " + enrollment.getCourse().getCourseId() + " ");
                System.out.print("Student ID: " + enrollment.getStudent().getStudentId() + " ");
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error getting all enrollments: " + e.getMessage());
        }
    }
}
