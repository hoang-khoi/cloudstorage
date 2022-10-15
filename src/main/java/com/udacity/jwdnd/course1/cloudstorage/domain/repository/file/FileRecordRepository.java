package com.udacity.jwdnd.course1.cloudstorage.domain.repository.file;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.FileRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileRecordRepository {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid) VALUES " +
            "(#{name}, #{mediaType}, #{size}, #{ownerUserId});")
    void save(FileRecord fileRecord);

    /**
     * @return null if not found.
     */
    @Select("SELECT fileId AS id , filename AS name, contenttype AS mediaType, filesize AS size, userid AS ownerUserId " +
            "FROM FILES " +
            "WHERE userid=#{ownerUserId} AND filename=#{key}")
    FileRecord getFileBelongToUser(long ownerUserId, String key);

    @Select("SELECT fileId AS id , filename AS name, contenttype AS mediaType, filesize AS size, userid AS ownerUserId " +
            "FROM FILES " +
            "WHERE userid=#{ownerUserId}")
    List<FileRecord> getFilesBelongToUser(long ownerUserId);

    @Delete("DELETE FROM FILES WHERE userid=#{ownerUserId} AND filename=#{key}")
    void deleteFileByName(long ownerUserId, String key);
}
