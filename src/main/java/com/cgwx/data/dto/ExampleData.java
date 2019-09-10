package com.cgwx.data.dto;

import java.util.List;

public class ExampleData {

    private String productId;
    private String productName;
    private String thumbnailUrl;
    private String countOfPeriods;
    private List<ExampleSingleData> exampleSingleDataList;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCountOfPeriods() {
        return countOfPeriods;
    }

    public void setCountOfPeriods(String countOfPeriods) {
        this.countOfPeriods = countOfPeriods;
    }

    public List<ExampleSingleData> getExampleSingleDataList() {
        return exampleSingleDataList;
    }

    public void setExampleSingleDataList(List<ExampleSingleData> exampleSingleDataList) {
        this.exampleSingleDataList = exampleSingleDataList;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
