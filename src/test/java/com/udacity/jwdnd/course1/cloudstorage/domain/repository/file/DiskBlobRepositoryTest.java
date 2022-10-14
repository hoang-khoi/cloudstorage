package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
class DiskBlobRepositoryTest {
    private static final String DUMMY_FILE_PATH_WITH_PARENT = "files/dummy.txt";
    private static final String DUMMY_FILE_PATH_WITHOUT_PARENT = "dummy.txt";
    private static final byte[] DUMMY_CONTENT_BYTES = "Sample content".getBytes(StandardCharsets.UTF_8);
    private static final MultipartFile DUMMY_MULTIPART_FILE = new MockMultipartFile("dummy.txt", DUMMY_CONTENT_BYTES);
    private static final File DUMMY_FILE_WITH_PARENT = new File(DUMMY_FILE_PATH_WITH_PARENT);
    private static final File DUMMY_FILE_WITHOUT_PARENT = new File(DUMMY_FILE_PATH_WITHOUT_PARENT);

    private final BlobRepository blobRepository = new DiskBlobRepository();

    @AfterEach
    void tearDown() {
        DUMMY_FILE_WITH_PARENT.delete();
        DUMMY_FILE_WITHOUT_PARENT.delete();
        DUMMY_FILE_WITH_PARENT.getParentFile().delete();
    }

    @Test
    void testSave_WithParentDirectories() throws IOException {
        assertFalse(DUMMY_FILE_WITH_PARENT.exists());
        blobRepository.save(DUMMY_MULTIPART_FILE, DUMMY_FILE_PATH_WITH_PARENT);
        assertTrue(DUMMY_FILE_WITH_PARENT.exists());
        assertArrayEquals(DUMMY_CONTENT_BYTES, Files.readAllBytes(DUMMY_FILE_WITH_PARENT.toPath()));
    }

    @Test
    void testSave_WithoutParentDirectories() throws IOException {
        assertFalse(DUMMY_FILE_WITHOUT_PARENT.exists());
        blobRepository.save(DUMMY_MULTIPART_FILE, DUMMY_FILE_PATH_WITHOUT_PARENT);
        assertTrue(DUMMY_FILE_WITHOUT_PARENT.exists());
        assertArrayEquals(DUMMY_CONTENT_BYTES, Files.readAllBytes(DUMMY_FILE_WITHOUT_PARENT.toPath()));
    }

    @Test
    void testGetFileByKey_NotFound_ReturnsNull() {
        File file = blobRepository.getFileByKey("not/existed/at.all");
        assertNull(file);
    }

    @Test
    void testGetFileByKey_Found_ReturnsTheCorrectFile() throws IOException {
        blobRepository.save(DUMMY_MULTIPART_FILE, DUMMY_FILE_PATH_WITH_PARENT);
        File file = blobRepository.getFileByKey(DUMMY_FILE_PATH_WITH_PARENT);
        assertTrue(file.exists());
        assertArrayEquals(DUMMY_CONTENT_BYTES, Files.readAllBytes(file.toPath()));
    }
}
