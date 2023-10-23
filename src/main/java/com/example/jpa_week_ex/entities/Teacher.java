package com.example.jpa_week_ex.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name="Teacher.findAll", query="SELECT t FROM Teacher t"),
        @NamedQuery(name="Teacher.deleteAll", query="DELETE FROM Teacher t")
})
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String fname;
    private String lname;
    @ManyToMany
    @JoinTable( // This is now the owner side of the relationsship
            name = "teacher_semester",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Semester> semesters = new HashSet<>();
    public Teacher() {
    }

    public Teacher(String fname, String lname) {
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

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(fname, teacher.fname) && Objects.equals(lname, teacher.lname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname, lname);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
