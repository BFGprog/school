package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);


    @Override
    public Faculty create(Faculty faculty) {
        logger.debug("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long id) {
        logger.debug("Was invoked method for find faculty");
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        logger.debug("Was invoked method for update faculty");
        return facultyRepository.findById(id).map(facultyDb -> {
            facultyDb.setName(faculty.getName());
            facultyDb.setColor(faculty.getColor());
            return facultyRepository.save(facultyDb);
        }).orElse(null);
    }

    @Override
    public Faculty delete(Long id) {
        logger.debug("Was invoked method for delete faculty");
        Faculty facultyDel = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return facultyDel;
    }

    @Override
    public List<Student> getStudent(Long id) {
        logger.debug("Was invoked method for delete students by faculty");
        return facultyRepository.findById(id).get().getStudent();
    }

    @Override
    public Faculty getLongFacultyName() {

        Faculty faculty = facultyRepository.findAll()
                .stream()
                .max(Comparator.comparingInt(f ->
                        f.getName().length()))
                .orElse(null);

//                .mapToInt(f -> f.getName().length())
//                .max();

        return faculty;
    }
}
