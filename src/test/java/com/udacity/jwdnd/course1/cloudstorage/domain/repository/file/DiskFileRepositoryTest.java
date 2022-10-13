package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
class DiskFileRepositoryTest {
    private static final String DUMMY_FILE_NAME = "dummy.txt";
    private final FileRepository fileRepository = new DiskFileRepository();
    private final File dummyFile = new File(DiskFileRepository.ROOT_DIR, DUMMY_FILE_NAME);

    private static MockMultipartFile makeMockedMultipartFile() {
        byte[] contentBytes = Charsets.UTF_8.encode("Sample content").array();
        return new MockMultipartFile(DUMMY_FILE_NAME, contentBytes);
    }

    @AfterEach
    void tearDown() {
        dummyFile.delete();
    }

    @Test
    void testSave() throws IOException {
        assertFalse(dummyFile.exists());

        fileRepository.save(makeMockedMultipartFile());
        byte[] actualBytes = Files.readAllBytes(dummyFile.toPath());

        assertTrue(dummyFile.exists());
        assertEquals(DUMMY_FILE_NAME, dummyFile.getName());
        assertArrayEquals(Charsets.UTF_8.encode("Sample content").array(), actualBytes);
    }

    @Test
    void testExists() throws IOException {
        assertFalse(fileRepository.existed(DUMMY_FILE_NAME));
        fileRepository.save(makeMockedMultipartFile());
        assertTrue(fileRepository.existed(DUMMY_FILE_NAME));
    }

    @Test
    void testDelete() throws IOException {
        fileRepository.save(makeMockedMultipartFile());
        assertTrue(fileRepository.existed(DUMMY_FILE_NAME));
        fileRepository.delete(DUMMY_FILE_NAME);
        assertFalse(fileRepository.existed(DUMMY_FILE_NAME));
    }

    @Test
    void testListAll() throws IOException {
        assertEquals(Collections.EMPTY_LIST, fileRepository.listAll());
        fileRepository.save(makeMockedMultipartFile());
        assertEquals(List.of(DUMMY_FILE_NAME), fileRepository.listAll());
    }

    @Test
    void testGetFileByName() throws IOException {
        fileRepository.save(makeMockedMultipartFile());
        File file = fileRepository.getFileByName(DUMMY_FILE_NAME);
        assertArrayEquals(
                Charsets.UTF_8.encode("Sample content").array(),
                Files.readAllBytes(file.toPath())
        );
    }
}
