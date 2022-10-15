package com.udacity.jwdnd.course1.cloudstorage.domain.repository.note;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class H2MyBatisNoteRepositoryTest {
    private static final String DUMMY_USERNAME = "mike_hunt";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    private User ownerUser;

    @BeforeEach
    void setUp() {
        if (userRepository.findByUsername(DUMMY_USERNAME) == null) {
            User user = new User();
            user.setUsername(DUMMY_USERNAME);

            userRepository.save(user);
        }
        ownerUser = userRepository.findByUsername(DUMMY_USERNAME);
        noteRepository.save(new Note(0, "title", "content", ownerUser.getId()));
    }

    @Test
    void testGetNotes_NoteExists() {
        List<Note> retrievedNotes = noteRepository.getNotes(ownerUser.getId());
        List<Note> expectedNotes = List.of(new Note(1, "title", "content", ownerUser.getId()));
        assertEquals(expectedNotes, retrievedNotes);
    }

    @Test
    void testGetNotes_NoteNotExists_ReturnsEmptyList() {
        List<Note> retrievedNotes = noteRepository.getNotes(23456);
        assertEquals(0, retrievedNotes.size());
    }

    @Test
    void testGetNoteById_NoteExist() {
        Note retrievedNote = noteRepository.getNoteById(ownerUser.getId(), 1);
        assertEquals(
                new Note(1L, "title", "content", ownerUser.getId()),
                retrievedNote
        );
    }

    @Test
    void testUpdateNoteById_NoteExist() {
        noteRepository.updateNoteById(ownerUser.getId(), 1, "New Title", "New Description");
        Note retrieved = noteRepository.getNoteById(ownerUser.getId(), 1);
        assertEquals(
                new Note(1L, "New Title", "New Description", ownerUser.getId()),
                retrieved
        );
    }

    @Test
    void testUpdateNoteById_NoteNotExist_NothingChanged() {
        noteRepository.updateNoteById(ownerUser.getId(), 431, "New Title", "New Description");
        Note retrieved = noteRepository.getNoteById(ownerUser.getId(), 1);
        assertEquals(
                new Note(1L, "title", "content", ownerUser.getId()),
                retrieved
        );
    }

    @Test
    void testDeleteNote() {
        assertNotNull(noteRepository.getNoteById(ownerUser.getId(), 1));
        noteRepository.deleteNoteById(ownerUser.getId(), 1);
        assertNull(noteRepository.getNoteById(ownerUser.getId(), 1));
    }
}
