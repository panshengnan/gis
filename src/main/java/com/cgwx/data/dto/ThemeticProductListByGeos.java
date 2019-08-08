package com.cgwx.data.dto;

import java.util.List;

public class ThemeticProductListByGeos {
    List<ThemeticProductSimpleInfo> themeticProductSimpleInfoList;
    String productId;
    String productName;
    String url;
    List<Industry> industryList;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setThemeticProductSimpleInfoList(List<ThemeticProductSimpleInfo> themeticProductSimpleInfoList) {
        this.themeticProductSimpleInfoList = themeticProductSimpleInfoList;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setIndustryList(List<Industry> industryList) {
        this.industryList = industryList;
    }

    public List<ThemeticProductSimpleInfo> getThemeticProductSimpleInfoList() {
        return themeticProductSimpleInfoList;
    }

    public String getProductId() {
        return productId;
    }

    public List<Industry> getIndustryList() {
        return industryList;
    }
}
