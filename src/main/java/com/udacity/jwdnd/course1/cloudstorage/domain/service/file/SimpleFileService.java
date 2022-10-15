package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.file.BlobRepository;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.file.FileRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleFileService implements FileService {
    private static final String BLOB_ROOT_KEY = "files";
    private final BlobRepository blobRepository;
    private final FileRecordRepository fileRecordRepository;

    private static String makeBlobKey(User user, String filename) {
        return String.format("%s/%s/%s", BLOB_ROOT_KEY, user.getUsername(), filename);
    }

    @Override
    public void save(User user, MultipartFile multipartFile) throws FileServiceException {
        String filename = multipartFile.getOriginalFilename();

        if (fileRecordRepository.getFileBelongToUser(user.getId(), filename) != null) {
            throw new FileServiceException("File already existed.");
        }

        String blobKey = makeBlobKey(user, filename);

        try {
            FileRecord fileRecord = new FileRecord(0, filename, multipartFile.getContentType(), multipartFile.getSize(), user.getId());
            blobRepository.save(multipartFile, blobKey);
            fileRecordRepository.save(fileRecord);
        } catch (IOException e) {
            throw new FileServiceException("Cannot save the blob.", e);
        }
    }

    @Override
    public List<FileRecord> listFileRecords(User user) {
        return fileRecordRepository.getFilesBelongToUser(user.getId());
    }

    @Override
    public File getFile(User user, String filename) {
        String blobKey = makeBlobKey(user, filename);
        return blobRepository.getFileByKey(blobKey);
    }

    @Override
    public void delete(User user, String filename) {
        fileRecordRepository.deleteFileByName(user.getId(), filename);
        blobRepository.deleteFileByKey(makeBlobKey(user, filename));
    }
}
