package com.example.jpa_week_ex.facades;

import com.example.jpa_week_ex.entities.Semester;
import com.example.jpa_week_ex.entities.Student;
import com.example.jpa_week_ex.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

public class Facade implements IFacade {
    EntityManagerFactory emf; // = Persistence.createEntityManagerFactory("pu");


    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Set<Student> getAllStudents() {
       EntityManager em = emf.createEntityManager();
       try {
           TypedQuery<Student> tq = em.createQuery("SELECT s FROM Student s", Student.class);
           return new HashSet<>(tq.getResultList());
       } finally {
           em.close();
       }
    }

    @Override
    public Set<Student> getStudentsByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> tq = em.createQuery("SELECT s FROM Student s WHERE s.fname = :fname", Student.class);
            tq.setParameter("fname", name);
            return new HashSet<>(tq.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public Student create(Student student) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } finally {
            em.close();
        }
    }

    @Override
    public Student assignStudent(Long semester_id, Long student_id) {
        EntityManager em = emf.createEntityManager();
        Semester semester = em.find(Semester.class, semester_id);
        Student student = em.find(Student.class, student_id);
        semester.addStudent(student);
        try {
            em.getTransaction().begin();
            Student merged = em.merge(student);
            em.merge(semester);
            em.getTransaction().commit();
            return merged;
        } finally {
            em.close();
        }

    }

    @Override
    public Set<Student> getByLastName(String lname) {

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> tq = em.createQuery("SELECT s FROM Student s WHERE s.lname = :lname", Student.class);
            tq.setParameter("lname", lname);
            return new HashSet<>(tq.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public int getNumberOfStudent(String semester_name) {

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> tq = em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.semester.name = :semester_name", Long.class);
            tq.setParameter("semester_name", semester_name);
            return tq.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public int getTotalNumberOfStudents() {

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> tq = em.createQuery("SELECT COUNT(s) FROM Student s", Long.class);
            return tq.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Teacher getTeacherWhoTeachersMostSemesters() {

        EntityManager em = emf.createEntityManager();
        try {
           TypedQuery<Teacher> tq = em.createQuery("SELECT t FROM Teacher t GROUP BY t ORDER BY COUNT(t.semesters) DESC", Teacher.class);
           tq.setMaxResults(1);
           return tq.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Semester getSemesterWithFewestStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Semester> tq = em.createQuery("SELECT s FROM Semester s GROUP BY s ORDER BY COUNT(s.students)", Semester.class);
            tq.setMaxResults(1);
            return tq.getSingleResult();
        } finally {
            em.close();
        }
    }

}
