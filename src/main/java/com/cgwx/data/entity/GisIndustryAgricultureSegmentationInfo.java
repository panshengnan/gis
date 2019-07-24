package com.cgwx.data.entity;

public class GisIndustryAgricultureSegmentationInfo {
    private Integer agricultureSegmentationCode;

    private String agricultureSegmentationDescription;

    public Integer getAgricultureSegmentationCode() {
        return agricultureSegmentationCode;
    }

    public void setAgricultureSegmentationCode(Integer agricultureSegmentationCode) {
        this.agricultureSegmentationCode = agricultureSegmentationCode;
    }

    public String getAgricultureSegmentationDescription() {
        return agricultureSegmentationDescription;
    }

    public void setAgricultureSegmentationDescription(String agricultureSegmentationDescription) {
        this.agricultureSegmentationDescription = agricultureSegmentationDescription == null ? null : agricultureSegmentationDescription.trim();
    }
}