package com.udacity.jwdnd.course1.cloudstorage.domain.entity;

import lombok.Data;
import org.springframework.http.MediaType;

@Data
public class FileRecord {
    private int id;
    private String name;
    private MediaType mediaType;
    private long size;
}
