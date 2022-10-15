package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface BlobRepository {
    void save(MultipartFile multipartFile, String key) throws IOException;

    /**
     * @return null if there is no file associated with the key.
     */
    File getFileByKey(String key);
    void deleteFileByKey(String key);
}
