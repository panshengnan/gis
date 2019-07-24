package com.cgwx.data.entity;

public class GisSensorInfo {
    private Integer sensorCode;

    private String sensorDescription;

    public Integer getSensorCode() {
        return sensorCode;
    }

    public void setSensorCode(Integer sensorCode) {
        this.sensorCode = sensorCode;
    }

    public String getSensorDescription() {
        return sensorDescription;
    }

    public void setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription == null ? null : sensorDescription.trim();
    }
}