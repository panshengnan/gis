package com.cgwx.data.dto;

import java.math.BigDecimal;

public class StandardProductDetail {
    private String productId;

    private String satelliteId;

    private String sensor;

    private String imagingTimeStr;

    private BigDecimal imageGsd;

    private BigDecimal cloudPercent;

    private String productQuality;

    private Object imageGeo;

    private Integer bandAmount;

    private String bandString;

    private String thumbnail;

    private Integer productType;

    private String productBand;

    private BigDecimal rollSatelliteAngle;

    private Float solarElevation;

    private Float centerLatitude;

    private Float centerLongitude;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(String satelliteId) {
        this.satelliteId = satelliteId;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getImagingTimeStr() {
        return imagingTimeStr;
    }

    public void setImagingTimeStr(String imagingTimeStr) {
        this.imagingTimeStr = imagingTimeStr;
    }

    public BigDecimal getImageGsd() {
        return imageGsd;
    }

    public void setImageGsd(BigDecimal imageGsd) {
        this.imageGsd = imageGsd;
    }

    public BigDecimal getCloudPercent() {
        return cloudPercent;
    }

    public void setCloudPercent(BigDecimal cloudPercent) {
        this.cloudPercent = cloudPercent;
    }

    public String getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(String productQuality) {
        this.productQuality = productQuality;
    }

    public Object getImageGeo() {
        return imageGeo;
    }

    public void setImageGeo(Object imageGeo) {
        this.imageGeo = imageGeo;
    }

    public Integer getBandAmount() {
        return bandAmount;
    }

    public void setBandAmount(Integer bandAmount) {
        this.bandAmount = bandAmount;
    }

    public String getBandString() {
        return bandString;
    }

    public void setBandString(String bandString) {
        this.bandString = bandString;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductBand() {
        return productBand;
    }

    public void setProductBand(String productBand) {
        this.productBand = productBand;
    }

    public BigDecimal getRollSatelliteAngle() {
        return rollSatelliteAngle;
    }

    public void setRollSatelliteAngle(BigDecimal rollSatelliteAngle) {
        this.rollSatelliteAngle = rollSatelliteAngle;
    }

    public Float getSolarElevation() {
        return solarElevation;
    }

    public void setSolarElevation(Float solarElevation) {
        this.solarElevation = solarElevation;
    }

    public Float getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(Float centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Float getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(Float centerLongitude) {
        this.centerLongitude = centerLongitude;
    }
}
