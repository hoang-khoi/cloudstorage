package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class H2MyBatisFileRecordRepositoryTest {
    private static final String DUMMY_USERNAME = "mike_hunt";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRecordRepository fileRecordRepository;

    private User ownerUser;

    @BeforeEach
    void setUp() {
        if (userRepository.findByUsername(DUMMY_USERNAME) == null) {
            User user = new User();
            user.setUsername(DUMMY_USERNAME);

            userRepository.save(user);
        }

        ownerUser = userRepository.findByUsername(DUMMY_USERNAME);
    }

    /**
     * This test is far from ideal, but it gets the job done, for now.
     */
    @Test
    void testSaveAndGetFilesBelongToUser_FileExist() {
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
    void testDeleteFileByKey_FileExist_SuccessfullyRemoved() {
        FileRecord fileRecord = dummyFileRecord(ownerUser);
        fileRecord.setName("mistake.pdf");

        fileRecordRepository.save(fileRecord);
        assertNotNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
        fileRecordRepository.deleteFileByKey(ownerUser.getId(), "mistake.pdf");
        assertNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
    }

    @Test
    void testDeleteFileByKey_FileNotExist_NothingHappens() {
        assertNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
        fileRecordRepository.deleteFileByKey(ownerUser.getId(), "mistake.pdf");
        assertNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
    }

    @Test
    void testGetFilesBelongToUser_FileNotExisted_ReturnsNull() {
        FileRecord retrievedFileRecord = fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "404.pdf");
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
