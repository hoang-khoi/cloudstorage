package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BlobRepository {
    void save(MultipartFile multipartFile, String key) throws IOException;
}
