package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student create(Student student) {
//        Long actualId = ++countId;
//        student.setId(actualId);
//        students.put(actualId, student);
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student update(Long id, Student student) {
//        logger.info("update ");
//        studentRepository.getReferenceById(id).setName(student.getName());
//        studentRepository.getById(id).setAge(student.getAge());
//        return studentRepository.save(student);
        return studentRepository.findById(id).map(studentDb -> {
            studentDb.setName(student.getName());
            studentDb.setAge(student.getAge());
            studentDb.setFaculty(student.getFaculty());
            return studentRepository.save(studentDb);
        }).orElse(null);
    }

    @Override
    public List<Student> getByAge(Integer age) {
        return studentRepository.findAllByAge(age);
    }

    @Override
    public List<Student> getBetweenAge(Integer from, Integer to) {
        return studentRepository.findByAgeBetween(from, to);
    }


    @Override
    public Student delete(Long id) {
        Student studentDel = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return studentDel;
    }

    //save() сохраняет изменения в БД;
    //findOne() находит первый элемент, который удовлетворяет определенным условиям;
    //findById() используется, если необходимо совершить поиск по идентификатору;
    //findAll() возвращает все записи из таблицы;
    //count() возвращает число int — количество записей в таблице;
    //deleteById() удаляет сущность из БД по ее идентификатору;

    @Override
    public Faculty getFaculty(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    @Override
    public Integer getNumberOfStudents() {
        return studentRepository.getNumberOfStudents();
    }

    @Override
    public int getAverageAge() {
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getFiveLastStudent() {
        return studentRepository.getFiveLastStudent();
    }


}
