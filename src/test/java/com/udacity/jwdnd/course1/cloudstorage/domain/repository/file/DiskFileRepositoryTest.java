package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
class DiskFileRepositoryTest {
    private static final String DUMMY_FILE_NAME = "dummy.txt";
    private final FileRepository fileRepository = new DiskFileRepository();
    private final File dummyFile = new File(DiskFileRepository.ROOT_DIR, DUMMY_FILE_NAME);


    @AfterEach
    void tearDown() {
        dummyFile.delete();
    }

    @Test
    void save() throws IOException {
        byte[] contentBytes = Charsets.UTF_8.encode("Sample content").array();

        assertFalse(dummyFile.exists());

        fileRepository.save(new MockMultipartFile(DUMMY_FILE_NAME, contentBytes));
        byte[] actualBytes = Files.readAllBytes(dummyFile.toPath());

        assertTrue(dummyFile.exists());
        assertEquals(DUMMY_FILE_NAME, dummyFile.getName());
        assertArrayEquals(contentBytes, actualBytes);
    }
}
