package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Repository
public class DiskFileRepository implements FileRepository {
    public static final File ROOT_DIR = new File("files/");

    @Override
    public void save(MultipartFile multipartFile) throws IOException {
        File file = new File(ROOT_DIR, multipartFile.getName());
        //noinspection ResultOfMethodCallIgnored
        ROOT_DIR.mkdir();
        multipartFile.transferTo(file);
    }
}
