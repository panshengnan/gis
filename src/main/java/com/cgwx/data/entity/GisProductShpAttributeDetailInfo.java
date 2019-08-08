package com.cgwx.data.entity;

public class GisProductShpAttributeDetailInfo {
    private Integer attributeId;

    private String attributeValue;

    private String fillColor;

    private String fillOpacity;

    private String strokeColor;

    private String strokeOpacity;

    private String otherValue;

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue == null ? null : attributeValue.trim();
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor == null ? null : fillColor.trim();
    }

    public String getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(String fillOpacity) {
        this.fillOpacity = fillOpacity == null ? null : fillOpacity.trim();
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor == null ? null : strokeColor.trim();
    }

    public String getStrokeOpacity() {
        return strokeOpacity;
    }

    public void setStrokeOpacity(String strokeOpacity) {
        this.strokeOpacity = strokeOpacity == null ? null : strokeOpacity.trim();
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue == null ? null : otherValue.trim();
    }
}