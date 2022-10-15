package com.udacity.jwdnd.course1.cloudstorage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRecord {
    private long id;
    private String name;
    private String mediaType;
    private long size;
    private long ownerUserId;

}
