package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    void save(User user, MultipartFile multipartFile) throws FileServiceException;

    List<FileRecord> listFileRecords(User user);

    File getFile(User user, String name);

    void delete(User user, String name);
}
