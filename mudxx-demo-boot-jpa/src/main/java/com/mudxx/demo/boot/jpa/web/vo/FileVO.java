package com.mudxx.demo.boot.jpa.web.vo;

import java.io.Serializable;

/**
 * @author laiwen
 */
public class FileVO implements Serializable {

    private static final long serialVersionUID = -2747680456193571531L;
    private String fileName;
    private String fileContent;
    private Integer fileSize;
    private String fileType;

    public FileVO() {
    }

    public FileVO(String fileName, String fileContent, Integer fileSize, String fileType) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "DataVO[fileName: " + this.fileName + ", fileSize: " + this.fileSize + ", fileType: " + this.fileType + "]";
    }
}
