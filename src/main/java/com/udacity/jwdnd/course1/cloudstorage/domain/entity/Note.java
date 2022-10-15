package com.udacity.jwdnd.course1.cloudstorage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private long id;
    private String title;
    private String description;
    private long ownerUserId;
}
