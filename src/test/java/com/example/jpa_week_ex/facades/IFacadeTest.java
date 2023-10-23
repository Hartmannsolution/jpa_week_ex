package com.example.jpa_week_ex.facades;

import com.example.jpa_week_ex.entities.Semester;
import com.example.jpa_week_ex.entities.Student;
import com.example.jpa_week_ex.entities.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class IFacadeTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    Student s1, s2, s3;
    Teacher t1, t2, t3;
    Semester sem1, sem2;
    IFacade facade = new Facade(emf);

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Student.deleteAll").executeUpdate();
            em.createNamedQuery("Teacher.deleteAll").executeUpdate();
            em.createNamedQuery("Semester.deleteAll").executeUpdate();
            s1 = new Student("Hans", "Hansen");
            s2 = new Student("Hanne", "Hamsun");
            s3 = new Student("Hamud", "Husseini");
            t1 = new Teacher("Tanja", "Teglgård");
            t2 = new Teacher("Tobias", "Tormodson");
            t3 = new Teacher("Taiko", "Takamoto");
            sem1 = new Semester("Third sem", "Very interesting semester");
            sem2 = new Semester("Fourth sem", "Amazing learning");
            sem1.addStudent(s1);
            sem1.addStudent(s2);
            sem2.addStudent(s3);
            sem1.addTeacher(t1);
            sem2.addTeacher(t2);
            sem2.addTeacher(t3);
            sem1.addTeacher(t3);
            em.persist(s1);
            em.persist(s2);
            em.persist(s3);
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.persist(sem1);
            em.persist(sem2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllStudents() {
        System.out.println("Get all students");
        int expected = 3;
        int actual = facade.getAllStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    void getStudentsByName() {
        System.out.println("Get students by name");
        Student expected = new Student("Hamud", "Husseini");
        Student actual = facade.getStudentsByName("Hamud").iterator().next();
        assertEquals(expected, actual);
    }

    @Test
    void create() {
        System.out.println("Test create a new Student");
        int expected = 4;
        Student palle = facade.create(new Student("Palle", "Pomodoro"));
        int actual = facade.getAllStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    void assignStudent() {
        System.out.println("Test assign Student to Semester");
        System.out.println("STUDENT SEMESTER: "+s1.getSemester().getName());
        Student changed = facade.assignStudent(sem2.getId(), s1.getId());
        String expected = sem2.getName();
        String actual = changed.getSemester().getName();
        assertEquals(expected, actual);
    }

    @Test
    void getByLastName() {
        System.out.println("Get by last name");
        Student expected = new Student("Hanne", "Hamsun");
        Student actual = facade.getByLastName("Hamsun").iterator().next();
        assertEquals(expected, actual);
    }

    @Test
    void getNumberOfStudent() {
        System.out.println("Get number of students");
        int expected = 1;
        int actual = facade.getNumberOfStudent("Fourth sem");
        assertEquals(expected, actual);
    }

    @Test
    void getTotalNumberOfStudents() {
        System.out.println("Get total number of students");
        int expected = 3;
        int actual = facade.getTotalNumberOfStudents();
        assertEquals(expected, actual);
    }

    @Test
    void getTeacherWhoTeachesMostSemesters() {
        System.out.println("Get teachers who teach the most number of semesters");
        Teacher expected = new Teacher("Taiko", "Takamoto");
        Teacher actual = facade.getTeacherWhoTeachersMostSemesters();
        assertEquals(expected, actual);
    }

}