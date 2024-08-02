package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
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
        studentRepository.getById(id).setName(student.getName());
        studentRepository.getById(id).setAge(student.getAge());
//        return studentRepository.save(student);
        return studentRepository.findById(id).get();
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
}
