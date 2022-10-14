package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiskFileService implements FileService {
    private final FileRepository fileRepository;

    @Override
    public void upload(MultipartFile multipartFile) throws FileServiceException {
        try {
            fileRepository.save(multipartFile);
        } catch (IOException e) {
            throw new FileServiceException("Failed to save the file.", e);
        }
    }

    @Override
    public void delete(String filename) {
        fileRepository.delete(filename);
    }

    @Override
    public List<String> listAllFilenames(String filename) {
        return fileRepository.listAll();
    }

    @Override
    public File getFileByName(String name) throws FileServiceException {
        File file = fileRepository.getFileByName(name);
        if (!file.exists()) {
            throw new FileServiceException("File not found.", null);
        }

        return file;
    }
}
