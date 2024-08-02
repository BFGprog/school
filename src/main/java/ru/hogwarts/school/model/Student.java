package ru.hogwarts.school.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Student {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE) //(strategy=GenerationType.SEQUENCE)//(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "student_id", initialValue = 1, allocationSize = 1)
    @SequenceGenerator(name = "generatorStudentId", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "generatorStudentId")
    private Long id;
    private String name;
    private int age;

//    public Student(){}
//    public Student(Long id, String name, int age) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(id, student.id) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
