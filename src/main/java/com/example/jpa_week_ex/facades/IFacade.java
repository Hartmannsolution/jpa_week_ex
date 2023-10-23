package com.example.jpa_week_ex.facades;

import com.example.jpa_week_ex.entities.Semester;
import com.example.jpa_week_ex.entities.Student;
import com.example.jpa_week_ex.entities.Teacher;

import java.util.Set;

public interface IFacade {

    Set<Student> getAllStudents();
    Set<Student> getStudentsByName(String name);
    Student create(Student student);
    Student assignStudent(Long semester_id, Long student_id);
    Set<Student> getByLastName(String lname);
    int getNumberOfStudent(String semester_name);
    int getTotalNumberOfStudents();
    Teacher getTeacherWhoTeachersMostSemesters();
    Semester getSemesterWithFewestStudents();
}
