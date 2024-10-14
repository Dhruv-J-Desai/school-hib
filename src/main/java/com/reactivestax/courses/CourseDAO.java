package com.reactivestax.courses;

import com.reactivestax.enrollments.Enrollment;
import com.reactivestax.students.Student;
import com.reactivestax.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class CourseDAO {

    public void addCourse(String courseName, Set<Student> students){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Course course = new Course();

            course.setCourseName(courseName);

            session.persist(course);

            for (Student student : students) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                session.persist(enrollment);
            }

            transaction.commit();

            System.out.println("Course '" + courseName + "' added.");
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    public void updateCourse(int courseId, String courseName){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            if (course != null) {
                course.setCourseName(courseName);
                session.update(course);
                transaction.commit();
                System.out.println("Course ID '" + courseId + "' updated.");
            } else {
                System.out.println("Course not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
    }

    public void removeCourse(int courseId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            if (course != null) {
                session.remove(course);
                transaction.commit();
                System.out.println("Course ID '" + courseId + "' removed.");
            } else {
                System.out.println("Course not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing course: " + e.getMessage());
        }
    }

    public void getCourses(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Course> courses = session.createQuery("from Course", Course.class).list();
            for (Course course : courses) {
                System.out.println(course.getCourseName());
            }
        } catch (Exception e) {
            System.out.println("Error getting courses: " + e.getMessage());
        }
    }

    public Course getCourseById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = null;

        try {
            course = session.get(Course.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return course;
    }
}
