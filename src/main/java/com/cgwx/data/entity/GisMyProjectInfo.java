package com.cgwx.data.entity;

public class GisMyProjectInfo {
    private String projectName;

    private String status;

    private Object flagGeo;

    private Object flagGeoTrend;

    private String jpg;

    private String userId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Object getFlagGeo() {
        return flagGeo;
    }

    public void setFlagGeo(Object flagGeo) {
        this.flagGeo = flagGeo;
    }

    public Object getFlagGeoTrend() {
        return flagGeoTrend;
    }

    public void setFlagGeoTrend(Object flagGeoTrend) {
        this.flagGeoTrend = flagGeoTrend;
    }

    public String getJpg() {
        return jpg;
    }

    public void setJpg(String jpg) {
        this.jpg = jpg == null ? null : jpg.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}