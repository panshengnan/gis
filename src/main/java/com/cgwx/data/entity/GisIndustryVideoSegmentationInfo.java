package com.cgwx.data.entity;

public class GisIndustryVideoSegmentationInfo {
    private Integer videoSegmentationCode;

    private String videoSegmentationDescription;

    public Integer getVideoSegmentationCode() {
        return videoSegmentationCode;
    }

    public void setVideoSegmentationCode(Integer videoSegmentationCode) {
        this.videoSegmentationCode = videoSegmentationCode;
    }

    public String getVideoSegmentationDescription() {
        return videoSegmentationDescription;
    }

    public void setVideoSegmentationDescription(String videoSegmentationDescription) {
        this.videoSegmentationDescription = videoSegmentationDescription == null ? null : videoSegmentationDescription.trim();
    }
}