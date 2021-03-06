package com.cgwx.data.entity;

import java.util.Date;

public class GisProductInfo {
    private String productId;

    private String productName;

    private Integer productType;

    private String productDescription;

    private String clientName;

    private String deliverName;

    private Date deliverTime;

    private String producer;

    private String archivePersonnel;

    private String produceArea;

    private Date produceTime;

    private String deliverMethod;

    private String dataProcessStep;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription == null ? null : productDescription.trim();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName == null ? null : deliverName.trim();
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ? null : producer.trim();
    }

    public String getArchivePersonnel() {
        return archivePersonnel;
    }

    public void setArchivePersonnel(String archivePersonnel) {
        this.archivePersonnel = archivePersonnel == null ? null : archivePersonnel.trim();
    }

    public String getProduceArea() {
        return produceArea;
    }

    public void setProduceArea(String produceArea) {
        this.produceArea = produceArea == null ? null : produceArea.trim();
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public String getDeliverMethod() {
        return deliverMethod;
    }

    public void setDeliverMethod(String deliverMethod) {
        this.deliverMethod = deliverMethod == null ? null : deliverMethod.trim();
    }

    public String getDataProcessStep() {
        return dataProcessStep;
    }

    public void setDataProcessStep(String dataProcessStep) {
        this.dataProcessStep = dataProcessStep == null ? null : dataProcessStep.trim();
    }
}