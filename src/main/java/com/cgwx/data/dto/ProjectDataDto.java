package com.cgwx.data.dto;

import java.util.List;

public class ProjectDataDto {

    private String layerName;

    private String dataName;

    private int dataId;

    private Object geo;

    private String tifOpacity;

    private String isEdit;

    private String attributeName;

    private String productId;

    private String singleId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSingleId() {
        return singleId;
    }

    public void setSingleId(String singleId) {
        this.singleId = singleId;
    }

    private List<AttributeInfoDto> attributeInfoDtoList;

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public Object getGeo() {
        return geo;
    }

    public void setGeo(Object geo) {
        this.geo = geo;
    }

    public String getTifOpacity() {
        return tifOpacity;
    }

    public void setTifOpacity(String tifOpacity) {
        this.tifOpacity = tifOpacity;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<AttributeInfoDto> getAttributeInfoDtoList() {
        return attributeInfoDtoList;
    }

    public void setAttributeInfoDtoList(List<AttributeInfoDto> attributeInfoDtoList) {
        this.attributeInfoDtoList = attributeInfoDtoList;
    }
}
