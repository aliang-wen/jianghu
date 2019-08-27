package com.aliang.aliangbok_entity.picfileentity;

import java.util.Date;

/**
 * @Description: 实体类 picFile
 */
public class PicFileEntity {
    private String fileCuuId;
    private String fileName;
    private String filePath;
    private String fileUrl;
    private String essayFCuuId;
    private Date fileCreatTimme;

    public PicFileEntity() {
    }

    public String getFileCuuId() {
        return fileCuuId;
    }

    public void setFileCuuId(String fileCuuId) {
        this.fileCuuId = fileCuuId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getEssayFCuuId() {
        return essayFCuuId;
    }

    public void setEssayFCuuId(String essayFCuuId) {
        this.essayFCuuId = essayFCuuId;
    }

    public Date getFileCreatTimme() {
        return fileCreatTimme;
    }

    public void setFileCreatTimme(Date fileCreatTimme) {
        this.fileCreatTimme = fileCreatTimme;
    }
}
