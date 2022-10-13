package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    void upload(MultipartFile multipartFile) throws FileServiceException;

    void delete(String filename);

    List<String> listAllFilenames(String filename);

    File getFileByName(String name) throws FileServiceException;
}
