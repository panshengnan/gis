package com.cgwx.data.entity;

import java.util.Date;

public class GisArchiveRecordsInfo {
    private String productId;

    private String productName;

    private Date archiveTime;

    private Integer archiveType;

    private String archivePersonnel;

    private Integer archiveResult;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Integer getArchiveType() {
        return archiveType;
    }

    public void setArchiveType(Integer archiveType) {
        this.archiveType = archiveType;
    }

    public String getArchivePersonnel() {
        return archivePersonnel;
    }

    public void setArchivePersonnel(String archivePersonnel) {
        this.archivePersonnel = archivePersonnel == null ? null : archivePersonnel.trim();
    }

    public Integer getArchiveResult() {
        return archiveResult;
    }

    public void setArchiveResult(Integer archiveResult) {
        this.archiveResult = archiveResult;
    }
}