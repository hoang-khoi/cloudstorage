package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileRepository {
    void save(MultipartFile multipartFile) throws IOException;

    boolean existed(String name);

    void delete(String name);

    List<String> listAll();

    File getFileByName(String filename);
}
