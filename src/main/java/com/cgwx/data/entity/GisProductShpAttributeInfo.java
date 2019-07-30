package com.cgwx.data.entity;

public class GisProductShpAttributeInfo {
    private String productId;

    private String singleId;

    private String attributeName;

    private String sldBody;

    private Integer valueCount;

    private String flag;

    private String otherName;

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

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName == null ? null : attributeName.trim();
    }

    public String getSldBody() {
        return sldBody;
    }

    public void setSldBody(String sldBody) {
        this.sldBody = sldBody == null ? null : sldBody.trim();
    }

    public Integer getValueCount() {
        return valueCount;
    }

    public void setValueCount(Integer valueCount) {
        this.valueCount = valueCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName == null ? null : otherName.trim();
    }
}