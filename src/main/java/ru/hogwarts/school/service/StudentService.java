package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    public Student delete(Long id);
    public Student create(Student student);
    public Student read(Long id) ;
    public Student update(Long id, Student student);
    public List<Student> getByAge(Integer age);
    public List<Student> getBetweenAge(Integer from, Integer to);
    public Faculty getFaculty(Long id);
    public Integer getNumberOfStudents();
    public int getAverageAge();
    public List<Student> getFiveLastStudent();
    public List<String> getStudentsStartingWithA();
    public Double getAverageAgeStream();
    public String getTimeMethod();





}
