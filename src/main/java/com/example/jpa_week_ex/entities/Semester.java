package com.example.jpa_week_ex.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name="Semester.findAll", query="SELECT s FROM Semester s"),
        @NamedQuery(name="Semester.deleteAll", query="DELETE FROM Semester s")
})
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "semesters")
    private Set<Teacher> teachers = new HashSet<>();
    @OneToMany(mappedBy = "semester")
    private Set<Student> students = new HashSet<>();

    public Semester() {
    }

    public Semester(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.addSemester(this);
    }
    public void removeTeacher(Teacher teacher){
        this.teachers.remove(teacher);
        teacher.getSemesters().remove(this);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.setSemester(this);
    }
    public void removeStudent(Student student){
        this.students.remove(student);
        student.setSemester(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return Objects.equals(name, semester.name) && Objects.equals(description, semester.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Semester{" +
                "name='" + name + '\'' +
                '}';
    }
}
