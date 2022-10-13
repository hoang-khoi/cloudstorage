package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public boolean existed(String name) {
        File file = new File(ROOT_DIR, name);
        return file.exists();
    }

    @Override
    public void delete(String name) {
        File file = new File(ROOT_DIR, name);
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    @Override
    public List<String> listAll() {
        //noinspection ConstantConditions
        return Arrays.asList(ROOT_DIR.list());
    }

    @Override
    public File getFileByName(String filename) {
        return new File(ROOT_DIR, filename);
    }
}
