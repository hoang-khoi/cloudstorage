package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.file.BlobRepository;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.file.FileRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SimpleFileService implements FileService {
    private static final String BLOB_ROOT_KEY = "files";
    private final BlobRepository blobRepository;
    private final FileRecordRepository fileRecordRepository;

    @Override
    public void save(User user, MultipartFile multipartFile) throws FileServiceException {
        String filename = multipartFile.getOriginalFilename();

        if (fileRecordRepository.getFileBelongToUser(user.getId(), filename) != null) {
            throw new FileServiceException("File already existed.");
        }

        String blobKey = String.format("%s/%s/%s", BLOB_ROOT_KEY, user.getUsername(), filename);

        try {
            FileRecord fileRecord = new FileRecord(0, filename, multipartFile.getContentType(), multipartFile.getSize(), user.getId());
            blobRepository.save(multipartFile, blobKey);
            fileRecordRepository.save(fileRecord);
        } catch (IOException e) {
            throw new FileServiceException("Cannot save the blob.", e);
        }
    }
}
