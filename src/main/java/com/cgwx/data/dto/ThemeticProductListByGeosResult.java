package com.cgwx.data.dto;

import java.util.List;

public class ThemeticProductListByGeosResult {
    private long totalItems;
    private int totalPageNum;
    private List<ThemeticProductListByGeos> productQueryList;

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<ThemeticProductListByGeos> getProductQueryList() {
        return productQueryList;
    }

    public void setProductQueryList(List<ThemeticProductListByGeos> productQueryList) {
        this.productQueryList = productQueryList;
    }
}
