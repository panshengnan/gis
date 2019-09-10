package com.cgwx.data.entity;

public class GisStandardProductType {
    private String productType;

    private Short typeIndex;

    private String typeDescription;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Short getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(Short typeIndex) {
        this.typeIndex = typeIndex;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription == null ? null : typeDescription.trim();
    }
}