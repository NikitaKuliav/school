package ru.hogwarts.school.service;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {


    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private final String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(@Value("${path.to.avatars.folder}") String avatarsDir,
                         AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarsDir = avatarsDir;
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<String> uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method uploadAvatar");
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        return ResponseEntity.ok().build();
    }
    private String getExtensions(String fileName) {
        logger.info("Was invoked method getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public ResponseEntity<byte[]> downloadAvatarFromDB(Long id) {
        logger.info("Was invoked method downloadAvatarFromDB");
        Avatar avatar = avatarRepository.findById(id).get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    public void downloadAvatar(Long id, HttpServletResponse response) throws IOException{
        logger.info("Was invoked method downloadAvatar");
        Avatar avatar = avatarRepository.findById(id).get();
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    public List<Avatar> getAvatars(int page, int size) {
        logger.info("Was invoked method getAvatars");
        Pageable pageable = PageRequest.of(page, size);
        return avatarRepository.findAll(pageable).getContent();
    }
}
