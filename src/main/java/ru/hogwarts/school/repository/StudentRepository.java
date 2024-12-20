package ru.hogwarts.school.repository;

import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findAllByAge(Integer age);
    public List<Student> findByAgeBetween(Integer from, Integer to);

    @Query(value = "select count(*) FROM student", nativeQuery = true)
    public Integer getNumberOfStudents();

    @Query(value = "select avg(age) FROM student", nativeQuery = true)
    public int getAverageAge();

    @Query(value = "select * FROM student order by id desc limit 5", nativeQuery = true)
    public List<Student> getFiveLastStudent();

}
