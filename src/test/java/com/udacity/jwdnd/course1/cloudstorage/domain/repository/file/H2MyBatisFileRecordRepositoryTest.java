package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@MybatisTest
class H2MyBatisFileRecordRepositoryTest {
    private static final String DUMMY_USERNAME = "mike_hunt";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRecordRepository fileRecordRepository;

    @BeforeEach
    void setUp() {
        if (userRepository.findByUsername(DUMMY_USERNAME) == null) {
            User user = new User();
            user.setUsername(DUMMY_USERNAME);

            userRepository.save(user);
        }
    }

    /**
     * This test is far from ideal, but it gets the job done, for now.
     */
    @Test
    void testSaveAndGetFilesBelongToUser_FileExist() {
        User ownerUser = userRepository.findByUsername(DUMMY_USERNAME);
        fileRecordRepository.save(dummyFileRecord(ownerUser));

        FileRecord expectedFileRecord = new FileRecord();
        expectedFileRecord.setId(1);
        expectedFileRecord.setMediaType("application/pdf");
        expectedFileRecord.setName("dummy.pdf");
        expectedFileRecord.setSize(6996);
        expectedFileRecord.setOwnerUserId(ownerUser.getId());

        List<FileRecord> expectedFileRecords = List.of(expectedFileRecord);

        FileRecord retrievedFileRecord = fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "dummy.pdf");
        assertEquals(expectedFileRecord, retrievedFileRecord);

        List<FileRecord> retrievedFileRecords = fileRecordRepository.getFilesBelongToUser(ownerUser.getId());
        assertEquals(expectedFileRecords, retrievedFileRecords);

    }

    @Test
    void testGetFilesBelongToUser_FileNotExisted_ReturnsNull() {
        FileRecord retrievedFileRecord = fileRecordRepository.getFileBelongToUser(1, "404.pdf");
        assertNull(retrievedFileRecord);
    }

    private FileRecord dummyFileRecord(User ownerUser) {
        FileRecord fileRecord = new FileRecord();
        fileRecord.setName("dummy.pdf");
        fileRecord.setMediaType(MediaType.APPLICATION_PDF_VALUE);
        fileRecord.setSize(6996);
        fileRecord.setOwnerUserId(12);
        fileRecord.setOwnerUserId(ownerUser.getId());

        return fileRecord;
    }
}
