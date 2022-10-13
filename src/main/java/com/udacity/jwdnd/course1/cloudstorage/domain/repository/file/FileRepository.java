package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileRepository {
    void save(MultipartFile multipartFile) throws IOException;
}
