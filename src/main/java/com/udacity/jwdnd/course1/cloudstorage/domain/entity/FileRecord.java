package com.udacity.jwdnd.course1.cloudstorage.domain.entity;

import lombok.Data;

@Data
public class FileRecord {
    private int id;
    private String name;
    private String mediaType;
    private long size;
    private long ownerUserId;

}
