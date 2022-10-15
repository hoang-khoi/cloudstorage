package com.udacity.jwdnd.course1.cloudstorage.domain.repository.note;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteRepository {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES " +
            "(#{title}, #{description}, #{ownerUserId})")
    void save(Note note);

    @Select("SELECT noteid AS id, notetitle AS title, notedescription AS description, userId AS ownerUserId " +
            "FROM NOTES WHERE userid=#{userId}")
    List<Note> getNotes(long userId);

    @Select("SELECT noteid AS id, notetitle AS title, notedescription AS description, userId AS ownerUserId " +
            "FROM NOTES WHERE userid=#{userId} AND noteid=#{id}")
    Note getNoteById(long userId, long id);

    @Update("UPDATE NOTES SET notetitle=#{title}, notedescription=#{description} WHERE " +
            "userid=#{userId} AND noteid=#{id}")
    void updateNoteById(long userId, long id, String title, String description);

    @Delete("DELETE FROM NOTES WHERE userid=#{userId} AND noteid=#{id}")
    void deleteNoteById(long userId, long id);
}
