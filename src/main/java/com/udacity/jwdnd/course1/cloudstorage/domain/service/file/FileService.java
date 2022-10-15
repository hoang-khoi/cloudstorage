package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void save(User user, MultipartFile multipartFile) throws FileServiceException;
}
