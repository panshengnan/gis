package com.cgwx.data.dto;

public class ClientFileInfo {
    private int productId;
//    private int parentId;
    private String productName;
    private String geoJson;
    private String thumbUrl;
    private String downloadUrl;

//    public int getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(int parentId) {
//        this.parentId = parentId;
//    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
