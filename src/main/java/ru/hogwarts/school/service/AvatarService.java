package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.nio.file.Path;

public interface AvatarService {

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Avatar findAvatar(Long id);

    public byte[] generateImageData(Path filePath) throws IOException;

}