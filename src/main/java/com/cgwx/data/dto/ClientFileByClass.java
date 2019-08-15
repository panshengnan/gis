package com.cgwx.data.dto;

import java.util.List;

public class ClientFileByClass {
    String Fileclass;
    List<ClientFileInfo> ProductList;

    public List<ClientFileInfo> getProductList() {
        return ProductList;
    }

    public void setProductList(List<ClientFileInfo> productList) {
        ProductList = productList;
    }

    public String getFileclass() {
        return Fileclass;
    }

    public void setFileclass(String fileclass) {
        Fileclass = fileclass;
    }
}
