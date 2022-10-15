package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.file.BlobRepository;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.file.FileRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimpleFileServiceTest {
    private static final User user = new User(1, "khoi", "secret", "Khoi", "Hoang");
    private static final MultipartFile mockMultipartFile = new MockMultipartFile("fileUpload", "archive.zip", "application/zip", new byte[]{1, 2, 3});
    @Mock
    private BlobRepository mockBlobRepository;
    @Mock
    private FileRecordRepository mockFileRecordRepository;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new SimpleFileService(mockBlobRepository, mockFileRecordRepository);
    }

    @Test
    void testSave_FileExisted_ExceptionThrown() {
        when(mockFileRecordRepository.getFileBelongToUser(1, "archive.zip")).thenReturn(new FileRecord());
        Exception e = assertThrows(FileServiceException.class, () -> fileService.save(user, mockMultipartFile));
        assertEquals("File already existed.", e.getMessage());
    }

    @Test
    void testSave_FailedBlobRepository_ExceptionThrown() throws IOException {
        doThrow(new IOException()).when(mockBlobRepository).save(mockMultipartFile, "files/khoi/archive.zip");
        Exception e = assertThrows(FileServiceException.class, () -> fileService.save(user, mockMultipartFile));
        assertEquals("Cannot save the blob.", e.getMessage());
        verify(mockFileRecordRepository, never()).save(any(FileRecord.class));
    }

    @Test
    void testSave_Success() throws FileServiceException, IOException {
        fileService.save(user, mockMultipartFile);
        FileRecord expectedFileRecord = new FileRecord(0, "archive.zip", "application/zip", 3, 1);
        verify(mockBlobRepository, times(1)).save(mockMultipartFile, "files/khoi/archive.zip");
        verify(mockFileRecordRepository, times(1)).save(expectedFileRecord);
    }
}
