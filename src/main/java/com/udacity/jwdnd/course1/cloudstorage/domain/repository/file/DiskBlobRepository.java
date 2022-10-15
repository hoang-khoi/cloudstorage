package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Repository
public class DiskBlobRepository implements BlobRepository {

    @Override
    public void save(MultipartFile multipartFile, String key) throws IOException {
        File file = new File(key);
        File parent = file.getParentFile();

        if (parent != null) {
            //noinspection ResultOfMethodCallIgnored
            parent.mkdirs();
        }

        multipartFile.transferTo(file);
    }

    @Override
    public File getFileByKey(String key) {
        File file = new File(key);
        return file.exists() ? file : null;
    }

    @Override
    public void deleteFileByKey(String key) {
        File file = new File(key);
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }
}
