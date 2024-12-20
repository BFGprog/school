package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
public class AvatarServiceImpl implements AvatarService {
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${path.to.avatars.folder}")
    private String avatarDir;

    private final StudentServiceImpl studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentServiceImpl studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }


    @Override
    @Transactional
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("Was invoked method for upload avatar");
        Student student = studentService.read(studentId);

        Path filePath = Path.of(avatarDir, studentId +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }


        Avatar avatar = findAvatar(studentId);

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImageData(filePath));

        avatarRepository.save(avatar);
    }

    @Override
    public Avatar findAvatar(Long id) {
        logger.debug("Was invoked method for find avatar");
        Avatar avatar = avatarRepository.findByStudentId(id);
        if (avatar == null) {
            avatar = new Avatar();
        }
        return avatar;
    }


    @Override
    @Transactional
    public byte[] generateImageData(Path filePath) throws IOException {
        logger.debug("Was invoked method for upload avatar in database");

        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getHeight() / 100);
            BufferedImage data = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = data.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(data,
                    filePath.getFileName().toString().substring(filePath.getFileName().toString().lastIndexOf(".") + 1),
                    baos);
            return baos.toByteArray();
        }
    }

    @Override
    public Page<Avatar> findAll(Pageable pageable) {
        logger.debug("Was invoked method for find all avatars");
        return avatarRepository.findAll(pageable);
    }

}