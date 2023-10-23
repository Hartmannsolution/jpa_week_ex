package com.example.jpa_week_ex.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name="Student.findAll", query="SELECT s FROM Student s"),
        @NamedQuery(name="Student.deleteAll", query="DELETE FROM Student s")
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String fname;
    private String lname;
    @ManyToOne
    @JoinColumn(name="semester_id", referencedColumnName = "id")
    private Semester semester;

    public Student() {
    }

    public Student(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(fname, student.fname) && Objects.equals(lname, student.lname);
    }

    @Override
    public String toString() {
        return "Student{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", semester=" + semester +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname, lname);
    }
}
