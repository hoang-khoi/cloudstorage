package com.udacity.jwdnd.course1.cloudstorage.domain.service.file;

public class FileServiceException extends Exception {
    public FileServiceException(String message) {
        super(message);
    }

    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
