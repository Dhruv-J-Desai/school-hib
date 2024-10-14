package com.reactivestax.grades;

import com.reactivestax.enrollments.Enrollment;
import com.reactivestax.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GradeDAO {
    public void addGrade(int courseId, int studentId, double grade){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String query = "SELECT e FROM Enrollment e WHERE e.course.courseId = :courseId AND e.student.studentId = :studentId";
            Enrollment enrollment = session.createQuery(query, Enrollment.class)
                    .setParameter("courseId", courseId)
                    .setParameter("studentId", studentId)
                    .uniqueResult();
            if (enrollment != null) {
                Grade newGrade = new Grade();
                newGrade.setCourse(enrollment.getCourse());
                newGrade.setStudent(enrollment.getStudent());
                newGrade.setGrade(grade);

                session.persist(newGrade);
                System.out.println("Grade added successfully for student ID " + studentId + " in course ID " + courseId);
            } else {
                System.out.println("Student with ID " + studentId + " is not enrolled in course ID " + courseId);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error adding grade: " + e.getMessage());
        }
    }

    public void removeGrade(int gradeId){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Grade grade = session.get(Grade.class, gradeId);
            if (grade != null) {
                session.remove(grade);
                System.out.println("Grade has been deleted.");
            } else {
                System.out.println("Grade not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error deleting grade: " + e.getMessage());
        }
    }

    public void updateGrade(int courseId, int studentId, double grade){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String query = "FROM Grade g WHERE g.course.courseId = :courseId AND g.student.studentId = :studentId";
            Grade existingGrade = session.createQuery(query, Grade.class)
                    .setParameter("courseId", courseId)
                    .setParameter("studentId", studentId)
                    .uniqueResult();
            if (existingGrade != null) {
                existingGrade.setGrade(grade);
                session.update(existingGrade);
                System.out.println("Grade has been updated.");
            } else {
                System.out.println("Grade not found for the specified course and student.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error updating grade: " + e.getMessage());
        }
    }
}
