package com.cgwx.data.entity;

public class GisMyProjectDataInfo {
    private String productId;

    private String singleId;

    private Integer projectId;

    private String dataPath;

    private String layerName;

    private String dataName;

    private String status;

    private String styleXml;

    private Object geo;

    private String tifOpacity;

    private String isEdit;

    private String jpg;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getSingleId() {
        return singleId;
    }

    public void setSingleId(String singleId) {
        this.singleId = singleId == null ? null : singleId.trim();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath == null ? null : dataPath.trim();
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName == null ? null : layerName.trim();
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName == null ? null : dataName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStyleXml() {
        return styleXml;
    }

    public void setStyleXml(String styleXml) {
        this.styleXml = styleXml == null ? null : styleXml.trim();
    }

    public Object getGeo() {
        return geo;
    }

    public void setGeo(Object geo) {
        this.geo = geo;
    }

    public String getTifOpacity() {
        return tifOpacity;
    }

    public void setTifOpacity(String tifOpacity) {
        this.tifOpacity = tifOpacity == null ? null : tifOpacity.trim();
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit == null ? null : isEdit.trim();
    }

    public String getJpg() {
        return jpg;
    }

    public void setJpg(String jpg) {
        this.jpg = jpg == null ? null : jpg.trim();
    }
}