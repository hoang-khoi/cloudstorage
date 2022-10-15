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
        // Setup test user
        if (userRepository.findByUsername(DUMMY_USERNAME) == null) {
            User user = new User();
            user.setUsername(DUMMY_USERNAME);

            userRepository.save(user);
        }

        // Set up existing files
        ownerUser = userRepository.findByUsername(DUMMY_USERNAME);
        fileRecordRepository.save(dummyFileRecord(ownerUser, "dummy.txt"));
        fileRecordRepository.save(dummyFileRecord(ownerUser, "foobar.txt"));
    }

    @Test
    void testGetFileBelongToUser_Exist_ReturnsCorrectObject() {
        FileRecord expectedFileRecord = new FileRecord(1, "dummy.txt", "application/pdf", 6996, ownerUser.getId());

        FileRecord retrievedFileRecord = fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "dummy.txt");
        assertEquals(expectedFileRecord, retrievedFileRecord);
    }

    @Test
    void testGetFileBelongToUser_NotExist_ReturnsNull() {
        FileRecord retrievedFileRecord = fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "dummy.lolz");
        assertNull(retrievedFileRecord);
    }

    @Test
    void testGetFilesBelongToUser_Exist_ReturnsCorrectList() {
        List<FileRecord> expectedFileRecords = List.of(
                new FileRecord(1, "dummy.txt", "application/pdf", 6996, ownerUser.getId()),
                new FileRecord(2, "foobar.txt", "application/pdf", 6996, ownerUser.getId())
        );
        List<FileRecord> retrievedFileRecords = fileRecordRepository.getFilesBelongToUser(ownerUser.getId());
        assertEquals(expectedFileRecords, retrievedFileRecords);
    }

    @Test
    void testGetFilesBelongToUser_NotExist_ReturnsEmptyList() {
        List<FileRecord> retrievedFileRecords = fileRecordRepository.getFilesBelongToUser(12873);
        assertEquals(0, retrievedFileRecords.size());
    }

    @Test
    void testDeleteFileByName_FileExist_SuccessfullyRemoved() {
        FileRecord fileRecord = dummyFileRecord(ownerUser, "mistake.pdf");

        fileRecordRepository.save(fileRecord);
        assertNotNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
        fileRecordRepository.deleteFileByName(ownerUser.getId(), "mistake.pdf");
        assertNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
    }

    @Test
    void testDeleteFileByName_FileNotExist_NothingHappens() {
        assertNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
        fileRecordRepository.deleteFileByName(ownerUser.getId(), "mistake.pdf");
        assertNull(fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "mistake.pdf"));
    }

    @Test
    void testGetFilesBelongToUser_FileNotExisted_ReturnsNull() {
        FileRecord retrievedFileRecord = fileRecordRepository.getFileBelongToUser(ownerUser.getId(), "404.pdf");
        assertNull(retrievedFileRecord);
    }

    private FileRecord dummyFileRecord(User ownerUser, String name) {
        FileRecord fileRecord = new FileRecord();
        fileRecord.setName(name);
        fileRecord.setMediaType(MediaType.APPLICATION_PDF_VALUE);
        fileRecord.setSize(6996);
        fileRecord.setOwnerUserId(12);
        fileRecord.setOwnerUserId(ownerUser.getId());

        return fileRecord;
    }
}
