package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student create(Student student) {
        logger.debug("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        logger.debug("Was invoked method for find student");
        return studentRepository.findById(id).get();
    }

    @Override
    public Student update(Long id, Student student) {
        logger.debug("Was invoked method for update student");
        return studentRepository.findById(id).map(studentDb -> {
            studentDb.setName(student.getName());
            studentDb.setAge(student.getAge());
            studentDb.setFaculty(student.getFaculty());
            return studentRepository.save(studentDb);
        }).orElse(null);
    }

    @Override
    public List<Student> getByAge(Integer age) {
        logger.debug("Was invoked method for find students by age");
        return studentRepository.findAllByAge(age);
    }

    @Override
    public List<Student> getBetweenAge(Integer from, Integer to) {
        logger.debug("Was invoked method for find students in a range of ages");
        return studentRepository.findByAgeBetween(from, to);
    }


    @Override
    public Student delete(Long id) {
        logger.debug("Was invoked method for delete student");
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
        logger.debug("Was invoked method for find faculty by student");
        return studentRepository.findById(id).get().getFaculty();
    }

    @Override
    public Integer getNumberOfStudents() {
        logger.debug("Was invoked method for student counting");
        return studentRepository.getNumberOfStudents();
    }

    @Override
    public int getAverageAge() {
        logger.debug("Was invoked method for calculated average age");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getFiveLastStudent() {
        logger.debug("Was invoked method for find five last students");
        return studentRepository.getFiveLastStudent();
    }

    @Override
    public List<String> getStudentsStartingWithA() {
        logger.debug("Was invoked method for get student names starting with «A»");

        List<String> names = studentRepository.findAll()
                .stream()
                .map(s -> s.getName())
                .map(s -> s.toLowerCase())
                .filter(s -> s.substring(0, 1).equals("a"))
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .sorted()
                .collect(Collectors.toList());

        return names;
    }

    @Override
    public Double getAverageAgeStream() {
        logger.debug("Was invoked method for get average age students");

        Double averageAge = studentRepository.findAll()
                .stream()
                .mapToDouble(s -> s.getAge())
                .average()
                .orElse(Double.NaN);

        return averageAge;
    }

    @Override
    public String getTimeMethod() {
        logger.debug("Was invoked method for get time");

        //first
        long startTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(100_000_000)
                .reduce(0, (a, b) -> a + b);
        long endTime = System.currentTimeMillis();
        logger.debug(String.valueOf(endTime - startTime));

        //second
        startTime = System.currentTimeMillis();
        int sum2 = 0;
        for (int i = 0; i <= 100_000_000; i++) {
            sum2 += i;
        }
        endTime = System.currentTimeMillis();
        logger.debug(String.valueOf(endTime - startTime));

        //Third
        startTime = System.currentTimeMillis();
        int sum3 = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(100_000_000)
                .reduce(0, (a, b) -> a + b);
        endTime = System.currentTimeMillis();
        logger.debug(String.valueOf(endTime - startTime));

        return "first: " + sum + "\n" +
                "second: " + sum2 + "\n" +
                "third: " + sum3 ;
    }

}
