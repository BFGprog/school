package ru.hogwarts.school.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    public Avatar findByStudentId(Long id);

//    @Query(value = "select * FROM student  limit 5", nativeQuery = true)
//    public ResponseEntity<Avatar> getPageAvatars(Integer number, Integer size);

    public Page<Avatar> findAll(Pageable pageable);

}
