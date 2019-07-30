package com.cgwx.data.entity;

public class GisProductLayerInfo {
    private String productId;

    private String singleId;

    private String layerName;

    private String sldPath;

    private String styleName;

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

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName == null ? null : layerName.trim();
    }

    public String getSldPath() {
        return sldPath;
    }

    public void setSldPath(String sldPath) {
        this.sldPath = sldPath == null ? null : sldPath.trim();
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName == null ? null : styleName.trim();
    }
}