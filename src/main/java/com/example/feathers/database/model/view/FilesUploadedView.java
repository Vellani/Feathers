package com.example.feathers.database.model.view;

public class FilesUploadedView {

    private int filesUploaded;

    public FilesUploadedView(Integer filesUploaded) {
        this.filesUploaded = filesUploaded;
    }

    public int getFilesUploaded() {
        return filesUploaded;
    }

    public void setFilesUploaded(int filesUploaded) {
        this.filesUploaded = filesUploaded;
    }
}
