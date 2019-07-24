package com.cgwx.data.entity;

public class GisSatelliteInfo {
    private Integer satelliteCode;

    private String satelliteDescription;

    public Integer getSatelliteCode() {
        return satelliteCode;
    }

    public void setSatelliteCode(Integer satelliteCode) {
        this.satelliteCode = satelliteCode;
    }

    public String getSatelliteDescription() {
        return satelliteDescription;
    }

    public void setSatelliteDescription(String satelliteDescription) {
        this.satelliteDescription = satelliteDescription == null ? null : satelliteDescription.trim();
    }
}